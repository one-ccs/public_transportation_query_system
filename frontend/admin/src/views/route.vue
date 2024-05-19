<template>
	<div>
		<div class="container">
            <div class="handle-box">
                <el-button type="danger" :icon="Delete" @click="deleteBatch()">批量删除</el-button>
                <el-button type="primary" :icon="Plus" @click="handleAdd()">新增</el-button>
                <el-button :icon="RefreshLeft" @click="getData()">刷新</el-button>

                <div class="float-end">
                    <el-date-picker
                        v-model="query.startDatetime"
                        type="datetime"
                        value-format="YYYY-MM-DD HH:mm:ss"
                        placeholder="开始日期"
                    />
                    <el-date-picker
                        v-model="query.endDatetime"
                        type="datetime"
                        value-format="YYYY-MM-DD HH:mm:ss"
                        placeholder="结束日期"
                    />
                    <el-input v-model="query.query" @change="handleSearch()" prefix-icon="Search" clearable placeholder="搜索线路号" class="handle-input mr10"></el-input>
                    <el-button type="primary" :icon="Search" @click="handleSearch()">搜索</el-button>
                </div>
			</div>
			<el-table
                ref="multipleTable"
                :data="tableData"
                :default-sort="{ prop: 'id', order: 'ascending' }"
                border
                @selection-change="handleSelectionChange"
                class="table"
                header-cell-class-name="table-header"
            >
                <el-table-column type="selection" width="39" />
				<el-table-column prop="id" label="ID" width="66" align="center" sortable></el-table-column>
				<el-table-column prop="no" label="线路号" width="125" align="center"></el-table-column>
				<el-table-column prop="price" label="票价" width="80" align="center">
                    <template #default="{ row }">
                        <span>{{ row.price }}元</span>
                    </template>
                </el-table-column>
				<el-table-column prop="firstTime" label="首班时间" width="125" align="center"></el-table-column>
				<el-table-column prop="lastTime" label="末班时间" width="125" align="center"></el-table-column>
				<el-table-column label="途经站点" min-width="200" show-overflow-tooltip>
                    <template #default="scope">
                        <el-tag
                            v-for="(item, index) in scope.row.stations"
                            :key="item.id"
							:type="item.status == 0 ? 'info': 'success'"
                        >
							{{ i18n(index + 1, 'serialNumber') }}{{ item.sitename }}
                        </el-tag>
                    </template>
                </el-table-column>
				<el-table-column label="开通状态" width="88" align="center">
					<template #default="scope">
						<el-tag
							:type="scope.row.status == 0 ? 'info': 'success'"
						>
							{{ scope.row.status == 0 ? '未开通': '已开通' }}
						</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="openingDatetime" label="开通日期" width="160" align="center" sortable></el-table-column>

				<el-table-column label="操作" width="200" align="center">
					<template #default="scope">
						<el-button text :icon="Edit" @click="handleModify(scope.row)" v-permiss="15">
							修改
						</el-button>
						<el-button text :icon="Delete" class="red" @click="handleDelete(scope.$index, scope.row)" v-permiss="16">
							删除
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div class="pagination">
				<el-pagination
					background
					layout="total, prev, pager, next, jumper, sizes"
					:current-page="query.pageIndex"
                    :page-sizes="[10, 20, 30, 50, 100]"
					:page-size="query.pageSize"
					:total="pageTotal"
                    @size-change="handleSizeChange"
					@current-change="handlePageChange"
				></el-pagination>
			</div>
		</div>

		<!-- 添加弹出框 -->
		<el-dialog title="添加" v-model="addVisible">
			<el-form :model="addForm" ref="addFormRef" :rules="addRules" label-width="70px">
				<el-form-item label="线路号" prop="no">
					<el-input
                        v-model="addForm.no"
                        placeholder="请输入线路号"
                    ></el-input>
				</el-form-item>
				<el-form-item label="票价(元)" prop="price">
					<el-input-number
                        v-model="modifyForm.price"
                        placeholder="票价"
                        :min="0"
                        :max="99"
                        :step="0.5"
                    ></el-input-number>
				</el-form-item>
				<el-form-item label="首班时间">
                    <el-time-select
                        v-model="addForm.firstTime"
                        start="00:00"
                        step="00:15"
                        end="23:59"
                        placeholder="请选择首班时间"
                    />
				</el-form-item>
				<el-form-item label="末班时间">
                    <el-time-select
                        v-model="addForm.lastTime"
                        start="00:00"
                        step="00:15"
                        end="23:59"
                        placeholder="请选择末班时间"
                    />
				</el-form-item>
                <el-form-item label="途径站点">
                    <el-select
                        v-model="addForm.stations"
                        v-infinite-load
                        value-key="id"
                        :teleported="false"
                        clearable
                        multiple
                        filterable
                        remote
                        reserve-keyword
                        placeholder="请选择途径站点（拖拽排序）"
                        :remote-method="stationRemote"
                        :loading="remoteLoading"
                        @change="updateSequence(addForm.stations)"
                    >
                        <template #tag>
                            <draggable
                                v-model="addForm.stations"
                                item-key="id"
                                @end="updateSequence(addForm.stations)"
                            >
                                <template #item="{ element }">
                                    <el-tag
                                        :type="element.status == 0 ? 'info': 'success'"
                                    >
                                        {{ i18n(element.sequence, 'serialNumber') }}{{ element.sitename }}
                                    </el-tag>
                                </template>
                            </draggable>
                        </template>
                        <template #default>
                            <el-option
                                v-for="item in stationList"
                                :key="item.id"
                                :label="item.sitename"
                                :value="item"
                            >
                                <el-tag
                                    :type="item.status == 0 ? 'info': 'success'"
                                >
                                    {{ item.sitename }}
                                </el-tag>
                            </el-option>
                        </template>
                    </el-select>
                </el-form-item>
                <el-form-item label="开通状态" prop="status">
                    <el-select
                        v-model="addForm.status"
                        placeholder="请选择状态"
                    >
                        <el-option
                            v-for="item in statusList"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        >
                            <el-tag
                                :type="item.value == 0 ? 'info': 'success'"
                            >
                                {{ item.value == 0 ? '未开通': '已开通' }}
                            </el-tag>
                        </el-option>
                    </el-select>
                </el-form-item>
				<el-form-item label="开通日期">
                    <el-date-picker
                        v-model="addForm.openingDatetime"
                        type="datetime"
                        value-format="YYYY-MM-DD HH:mm:ss"
                        placeholder="请选择开通日期"
                    />
				</el-form-item>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="addVisible = false">取 消</el-button>
					<el-button type="primary" @click="saveAdd(addFormRef)">确 定</el-button>
				</span>
			</template>
		</el-dialog>

		<!-- 修改弹出框 -->
		<el-dialog title="修改" v-model="modifyVisible">
			<el-form :model="modifyForm" ref="modifyFormRef" :rules="modifyRules" label-width="70px">
				<el-form-item label="ID" prop="id">
					<el-input v-model="modifyForm.id" disabled></el-input>
				</el-form-item>
				<el-form-item label="线路号" prop="no">
					<el-input
                        v-model="modifyForm.no"
                        placeholder="请输入线路号"
                    ></el-input>
				</el-form-item>
				<el-form-item label="票价(元)" prop="price">
					<el-input-number
                        v-model="modifyForm.price"
                        placeholder="票价"
                        :min="0"
                        :max="99"
                        :step="0.5"
                    ></el-input-number>
				</el-form-item>
				<el-form-item label="首班时间">
                    <el-time-select
                        v-model="modifyForm.firstTime"
                        start="00:00"
                        step="00:15"
                        end="23:59"
                        placeholder="请选择首班时间"
                    />
				</el-form-item>
				<el-form-item label="末班时间">
                    <el-time-select
                        v-model="modifyForm.lastTime"
                        start="00:00"
                        step="00:15"
                        end="23:59"
                        placeholder="请选择末班时间"
                    />
				</el-form-item>
                <el-form-item label="途径站点">
                    <el-select
                        v-model="modifyForm.stations"
                        v-infinite-load
                        value-key="id"
                        :teleported="false"
                        clearable
                        multiple
                        filterable
                        remote
                        reserve-keyword
                        placeholder="请选择途径站点（拖拽排序）"
                        :remote-method="stationRemote"
                        :loading="remoteLoading"
                        @change="updateSequence(modifyForm.stations)"
                    >
                        <template #tag>
                            <draggable
                                v-model="modifyForm.stations"
                                item-key="id"
                                @end="updateSequence(modifyForm.stations)"
                            >
                                <template #item="{ element }">
                                    <el-tag
                                        :type="element.status == 0 ? 'info': 'success'"
                                    >
                                        {{ i18n(element.sequence, 'serialNumber') }}{{ element.sitename }}
                                    </el-tag>
                                </template>
                            </draggable>
                        </template>
                        <template #default>
                            <el-option
                                v-for="item in stationList"
                                :key="item.id"
                                :label="item.sitename"
                                :value="item"
                            >
                                <el-tag
                                    :type="item.status == 0 ? 'info': 'success'"
                                >
                                    {{ item.sitename }}
                                </el-tag>
                            </el-option>
                        </template>
                    </el-select>
                </el-form-item>
                <el-form-item label="开通状态" prop="status">
                    <el-select
                        v-model="modifyForm.status"
                        placeholder="请选择状态"
                    >
                        <el-option
                            v-for="item in statusList"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        >
                            <el-tag
                                :type="item.value == 0 ? 'info': 'success'"
                            >
                                {{ item.value == 0 ? '未开通': '已开通' }}
                            </el-tag>
                        </el-option>
                    </el-select>
                </el-form-item>
				<el-form-item label="开通日期">
                    <el-date-picker
                        v-model="modifyForm.openingDatetime"
                        type="datetime"
                        value-format="YYYY-MM-DD HH:mm:ss"
                        placeholder="请选择开通日期"
                    />
				</el-form-item>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="modifyVisible = false">取 消</el-button>
					<el-button type="primary" @click="saveModify(modifyFormRef)">确 定</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script setup lang="ts" name="route">
import { onMounted, reactive, ref } from 'vue';
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus';
import { Delete, Edit, Plus, RefreshLeft, Search } from '@element-plus/icons-vue';
import draggable from 'vuedraggable';
import type { ResponseData, RouteBO, Station, PageQuery, TimeRangePageQuery, StationBO } from '@/utils/interface';
import { apiNoticePut, apiRouteDelete, apiRoutePageQuery, apiRoutePost, apiRoutePut, apiStationPageQuery } from '@/utils/api';
import { deepCopy } from '@/utils/copy';
import { strftime } from '@/utils/datetime';
import i18n from '@/utils/i18n';
import useUserStore from '@/stores/user';


const userStore = useUserStore();
const query = reactive<TimeRangePageQuery>({
	pageIndex: 1,
	pageSize: 10,
	query: '',
    startDatetime: '',
    endDatetime: '',
});
const tableData = ref<RouteBO[]>([]);
const pageTotal = ref(0);
// 获取表格数据
const getData = () => {
	apiRoutePageQuery(query, (data: ResponseData) => {
        ElMessage.success('线路数据获取成功');
		tableData.value = data.data.list;
		pageTotal.value = data.data.total || 50;
    }, (data: ResponseData) => {
        ElMessage.warning('线路数据获取失败');
    });
};
getData();

// 远程加载站点列表
const stationList = ref<Station[]>([]);
const remoteLoading = ref(false);
const remoteLoadingFinished = ref(false);
const stationQuery = reactive<PageQuery>({
	pageIndex: 1,
	pageSize: 10,
	query: '',
});
let lastQuery: string | null = null;
const stationRemote = (query: string) => {
    if (query == lastQuery) return;
    lastQuery = query;

    // 重置相关变量
    stationQuery.pageIndex = 1;
    stationQuery.query = query;
    remoteLoadingFinished.value = false;

    remoteLoading.value = true;
    apiStationPageQuery(stationQuery, (data: ResponseData) => {
        remoteLoading.value = false;
        stationList.value = data.data.list;
    }, (data: ResponseData) => {
        remoteLoading.value = false;
        ElMessage.warning('站点列表获取失败');
    });
};
stationRemote('');

// 触底加载站点列表
const infiniteLoad = () => {
    stationQuery.pageIndex++;

    apiStationPageQuery(stationQuery, (data: ResponseData) => {
        remoteLoading.value = false;
        remoteLoadingFinished.value = data.data.finished;
        stationList.value.push(...data.data.list);
    }, (data: ResponseData) => {
        remoteLoading.value = false;
        ElMessage.warning('站点列表获取失败');
    });
};

// 触底加载指令
const vInfiniteLoad = {
    mounted: (el: Element, binding: any) => {
        const scrollElement = el.querySelector('.el-select-dropdown__wrap');
        scrollElement?.addEventListener('scroll', (event: Event) => {
            const bottom = scrollElement.scrollHeight - scrollElement.scrollTop - scrollElement.clientHeight;

            // 加载下一页
            if (bottom <= 5 && !remoteLoadingFinished.value && !remoteLoading.value) {
                infiniteLoad();
            }
        });
    },
};

// 多选操作
const multipleSelection = ref<RouteBO[]>([]);
const handleSelectionChange = (val: RouteBO[]) => {
    multipleSelection.value = val;
};
// 批量删除
const deleteBatch = () => {
    if (!multipleSelection.value.length) return ElMessage.warning('未选择任何数据');

	// 二次确认删除
	ElMessageBox.confirm(`确定要删除 "${multipleSelection.value.length}" 条数据吗？`, '提示', {
		type: 'warning'
	})
    .then(() => {
        apiRouteDelete(multipleSelection.value.map(item => item.id!), () => {
            ElMessage.success('删除成功');
            getData();
        });
    })
    .catch(() => {});
};

// 查询操作
let searchLocked = false;
const handleSearch = () => {
    if (searchLocked) return;
    searchLocked = true;
    setTimeout(() => {
        searchLocked = false;
    }, 300);

	query.pageIndex = 1;
	getData();
};
// 分页导航
const handlePageChange = (val: number) => {
	query.pageIndex = val;
	getData();
};
const handleSizeChange = (val: number) => {
    query.pageSize = val;
    getData();
};

// 删除操作
const handleDelete = (index: number, row: any) => {
	// 二次确认删除
	ElMessageBox.confirm(`确定要删除 "${row.no}" 吗？`, '提示', {
		type: 'warning'
	})
    .then(() => {
        apiRouteDelete(row.id, () => {
            ElMessage.success('删除成功');
            getData();
            apiNoticePut({
                title: '线路移除通知',
                content: `系统管理员 ${userStore.userInfo.username} 于 ${strftime(new Date())} 移除线路 ${row.no}`,
            }, () => {});
        });
    })
    .catch(() => {});
};

// 状态列表
const statusList = reactive([
    {value: 0, label: '未开通'},
    {value: 1, label: '已开通'},
]);

// 添加表格验证
const addRules: FormRules = {
	no: [{ required: true, message: '请输入线路号', trigger: 'blur' }],
};
// 表格添加时弹窗和保存
const addVisible = ref(false);
const addFormRef = ref<FormInstance>();
const addForm = reactive<RouteBO>({
    status: 1,
});
// 点击添加按钮
const handleAdd = () => {
	addVisible.value = true;
};
// 保存添加
const saveAdd = (formEl: FormInstance | undefined) => {
    formEl && formEl.validate((valid: boolean) => {
        if (!valid) return;

        apiRoutePut(addForm, (data: ResponseData) => {
            ElMessage.success(data.message);
            addVisible.value = false;
            getData();
            apiNoticePut({
                title: '线路添加通知',
                content: `系统管理员 ${userStore.userInfo.username} 于 ${strftime(new Date())} 新增线路 ${addForm.no}`,
            }, () => {});
        });
    });
};

// 修改表格验证
const modifyRules: FormRules = {
	no: [{ required: true, message: '请输入线路号', trigger: 'blur' }],
};
// 表格修改时弹窗和保存
const modifyVisible = ref(false);
const modifyFormRef = ref<FormInstance>();
const modifyForm = reactive<RouteBO>({});
let oldModifyString = '';
// 点击修改按钮
const handleModify = (row: any) => {
    deepCopy(modifyForm, row);
    oldModifyString = JSON.stringify(modifyForm);
	modifyVisible.value = true;
};
// 保存修改
const saveModify = (formEl: FormInstance | undefined) => {
    formEl && formEl.validate((valid: boolean) => {
        if (!valid) return;

        // 表单未修改
        if (JSON.stringify(modifyForm) === oldModifyString) return ElMessage.warning('未修改');
        apiRoutePost(modifyForm, (data: ResponseData) => {
            modifyVisible.value = false;
            ElMessage.success(`修改成功 (ID ${modifyForm.id})`);
            getData();
        });
    });
};

// 更新站点排序
const updateSequence = (stations?: StationBO[]) => {
    stations?.forEach((station, index) => station.sequence = index + 1);
};
</script>

<style scoped>
.handle-box {
	margin-bottom: 20px;
}

.handle-select {
	width: 120px;
}

.handle-input {
	width: 300px;
}
.table {
	width: 100%;
	font-size: 14px;
}
.red {
	color: #F56C6C;
}
.mr10 {
	margin-right: 10px;
}
.table-td-thumb {
	display: block;
	margin: auto;
	width: 40px;
	height: 40px;
}
</style>
