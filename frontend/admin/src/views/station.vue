<template>
	<div>
		<div class="container">
            <div class="handle-box">
                <el-button type="danger" :icon="Delete" @click="deleteBatch()">批量删除</el-button>
                <el-button type="primary" :icon="Plus" @click="handleAdd()">新增</el-button>
                <el-button type="primary" :icon="Download" @click="exportXlsx()">导出Excel</el-button>
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
                    <el-input v-model="query.query" @change="handleSearch()" prefix-icon="Search" clearable placeholder="搜索站点名" class="handle-input mr10"></el-input>
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
				<el-table-column prop="sitename" label="站点名" align="center" sortable></el-table-column>
				<el-table-column prop="longitude" label="经度" align="center"></el-table-column>
				<el-table-column prop="latitude" label="纬度" align="center"></el-table-column>
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
		<el-dialog title="添加" v-model="addVisible" width="30%">
			<el-form :model="addForm" ref="addFormRef" :rules="addRules" label-width="70px">
				<el-form-item label="站点名" prop="sitename">
					<el-input v-model="addForm.sitename"></el-input>
				</el-form-item>
				<el-form-item label="经度" prop="longitude">
					<el-input-number v-model="addForm.longitude" :min="0" :max="180"></el-input-number>
				</el-form-item>
				<el-form-item label="纬度" prop="latitude">
					<el-input-number v-model="addForm.latitude" :min="0" :max="90"></el-input-number>
				</el-form-item>
                <el-form-item label="开通状态" prop="status">
                    <el-select
                        v-model="addForm.status"
                        placeholder="请选择状态"
                    >
                        <el-option
                            v-for="item in statusList" :key="item.value"
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
		<el-dialog title="修改" v-model="modifyVisible" width="30%">
			<el-form :model="modifyForm" ref="modifyFormRef" :rules="modifyRules" label-width="70px">
				<el-form-item label="ID" prop="id">
					<el-input v-model="modifyForm.id" disabled></el-input>
				</el-form-item>
				<el-form-item label="站点名" prop="sitename">
					<el-input v-model="modifyForm.sitename"></el-input>
				</el-form-item>
				<el-form-item label="经度" prop="longitude">
					<el-input-number v-model="modifyForm.longitude" :min="0" :max="180"></el-input-number>
				</el-form-item>
				<el-form-item label="纬度" prop="latitude">
					<el-input-number v-model="modifyForm.latitude" :min="0" :max="90"></el-input-number>
				</el-form-item>
                <el-form-item label="开通状态" prop="status">
                    <el-select
                        v-model="modifyForm.status"
                        placeholder="请选择状态"
                    >
                        <el-option
                            v-for="item in statusList" :key="item.value"
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
                        placeholder="请选择开通时间"
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

<script setup lang="ts" name="station">
import { Delete, Edit, Plus, Search, RefreshLeft, Download } from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus';
import { reactive, ref } from 'vue';
import type { ResponseData, Station, TimeRangePageQuery } from '@/utils/interface';
import { apiNoticePut, apiStationDelete, apiStationPageQuery, apiStationPost, apiStationPut } from '@/utils/api';
import { deepCopy } from '@/utils/copy';
import { strftime } from '@/utils/datetime';
import useUserStore from '@/stores/user';
import { exportExcel } from '@/utils/excel';


const userStore = useUserStore();
const query = reactive<TimeRangePageQuery>({
	pageIndex: 1,
	pageSize: 10,
	query: '',
    startDatetime: '',
    endDatetime: '',
});
const tableData = ref<Station[]>([]);
const pageTotal = ref(0);
// 获取表格数据
const getData = () => {
	apiStationPageQuery(query, (data: ResponseData) => {
        ElMessage.success('站点数据获取成功');
		tableData.value = data.data.list;
		pageTotal.value = data.data.total || 50;
    }, (data: ResponseData) => {
        ElMessage.warning('站点数据获取失败');
    });
};
getData();

// 多选操作
const multipleSelection = ref<Station[]>([]);
const handleSelectionChange = (val: Station[]) => {
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
        apiStationDelete(multipleSelection.value.map(item => item.id!), () => {
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
	ElMessageBox.confirm(`确定要删除 "${row.sitename}" 吗？`, '提示', {
		type: 'warning'
	})
    .then(() => {
        apiStationDelete(row.id, () => {
            ElMessage.success('删除成功');
            getData();
            apiNoticePut({
                title: '站点移除通知',
                content: `系统管理员 ${userStore.userInfo.username} 于 ${strftime(new Date())} 移除站点 ${row.sitename}`,
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
const addForm = reactive<Station>({
    sitename: '',
    longitude: -1,
    latitude: -1,
    status: 0,
    openingDatetime: '',
});
const handleAdd = () => {
	addVisible.value = true;
};
const saveAdd = (formEl: FormInstance | undefined) => {
    formEl && formEl.validate((valid: boolean) => {
        if (!valid) return;

        apiStationPut(addForm, (data: ResponseData) => {
            ElMessage.success(data.message);
            addVisible.value = false;
            getData();
            apiNoticePut({
                title: '站点添加通知',
                content: `系统管理员 ${userStore.userInfo.username} 于 ${strftime(new Date())} 新增站点 ${addForm.sitename}`,
            }, () => {});
        });
    });
};
const exportXlsx = () => {
    const list = tableData.value.map(item => [item.id, item.sitename, item.longitude, item.latitude, item.status, item.openingDatetime]);

    list.unshift(['ID', '站点名', '经度', '纬度', '开通状态', '开通日期']);

    exportExcel(`站点_第${query.pageIndex}页_${new Date().valueOf()}`, list);
};

// 修改表格验证
const modifyRules: FormRules = {
	no: [{ required: true, message: '请输入线路号', trigger: 'blur' }],
};
// 表格修改时弹窗和保存
const modifyVisible = ref(false);
const modifyFormRef = ref<FormInstance>();
const modifyForm = reactive<Station>({
    sitename: '',
    longitude: -1,
    latitude: -1,
    status: 0,
    openingDatetime: '',
});
let oldModifyString = '';
const handleModify = (row: any) => {
    deepCopy(modifyForm, row);
    oldModifyString = JSON.stringify(modifyForm);
	modifyVisible.value = true;
};
const saveModify = (formEl: FormInstance | undefined) => {
    formEl && formEl.validate((valid: boolean) => {
        if (!valid) return;

        // 表单未修改
        if (JSON.stringify(modifyForm) === oldModifyString) return ElMessage.warning('未修改');
        apiStationPost(modifyForm, (data: ResponseData) => {
            modifyVisible.value = false;
            ElMessage.success(`修改成功 (ID ${modifyForm.id})`);
            getData();
        });
    });
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
