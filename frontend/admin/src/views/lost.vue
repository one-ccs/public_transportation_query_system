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
                    <el-input v-model="query.query" @change="handleSearch()" prefix-icon="Search" clearable placeholder="搜索发布用户、标题或内容" class="handle-input mr10"></el-input>
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
				<el-table-column prop="describe" label="描述" show-overflow-tooltip></el-table-column>
				<el-table-column prop="address" label="拾取地点" show-overflow-tooltip></el-table-column>
				<el-table-column label="图片" align="center">
					<template #default="{ row }">
						<el-image
							class="table-td-thumb"
                            fit="cover"
							:src="row.imgUrl.includes('http') ? row.imgUrl : globalStore.lostApi + row.imgUrl"
							:z-index="10"
							:preview-src-list="[row.imgUrl.includes('http') ? row.imgUrl : globalStore.lostApi + row.imgUrl]"
							preview-teleported
                            hide-on-click-modal
						>
						</el-image>
					</template>
				</el-table-column>
				<el-table-column prop="pickDatetime" label="拾取时间" width="160" align="center" sortable></el-table-column>
				<el-table-column prop="claimDatetime" label="认领时间" width="160" align="center" sortable></el-table-column>
				<el-table-column label="认领状态" width="100" align="center">
					<template #default="scope">
						<el-tag
							:type="scope.row.status == 0 ? 'info': 'success'"
						>
							{{ scope.row.status == 0 ? '待认领': '已认领' }}
						</el-tag>
					</template>
				</el-table-column>

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
		<el-dialog title="添加" v-model="addVisible" width="500px">
			<el-form :model="addForm" ref="addFormRef" :rules="addRules" label-width="80px">
				<el-form-item label="物品描述" prop="describe">
					<el-input v-model="addForm.describe" type="textarea" :autosize="{ minRows: 2, maxRows: 5 }"></el-input>
				</el-form-item>
				<el-form-item label="拾取地址" prop="address">
                    <el-input v-model="addForm.address"></el-input>
				</el-form-item>
				<el-form-item label="拾取时间" prop="pickDatetime">
                    <el-date-picker
                        v-model="addForm.pickDatetime"
                        type="datetime"
                        value-format="YYYY-MM-DD HH:mm:ss"
                        placeholder="请选择拾取时间"
                    />
				</el-form-item>
				<el-form-item label="相关图片" prop="imgUrl">
                    <image-upload :image-src="addForm.imgUrl" :upload-handler="uploadHandler"  :width="150" :height="100" />
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
		<el-dialog title="修改" v-model="modifyVisible" width="500px">
			<el-form :model="modifyForm" ref="modifyFormRef" :rules="modifyRules" label-width="80px">
				<el-form-item label="ID" prop="id">
					<el-input v-model="modifyForm.id" disabled></el-input>
				</el-form-item>
				<el-form-item label="物品描述" prop="describe">
					<el-input v-model="modifyForm.describe" type="textarea" :autosize="{ minRows: 2, maxRows: 5 }"></el-input>
				</el-form-item>
				<el-form-item label="拾取地址" prop="address">
                    <el-input v-model="modifyForm.address"></el-input>
				</el-form-item>
				<el-form-item label="拾取时间" prop="pickDatetime">
                    <el-date-picker
                        v-model="modifyForm.pickDatetime"
                        type="datetime"
                        value-format="YYYY-MM-DD HH:mm:ss"
                        placeholder="请选择拾取时间"
                    />
				</el-form-item>
				<el-form-item label="认领时间" prop="claimDatetime">
                    <el-date-picker
                        v-model="modifyForm.claimDatetime"
                        type="datetime"
                        value-format="YYYY-MM-DD HH:mm:ss"
                        placeholder="请选择认领时间"
                    />
				</el-form-item>
				<el-form-item label="相关图片" prop="imgUrl">
                    <image-upload :image-src="modifyForm.imgUrl" :upload-handler="uploadHandler" :width="150" :height="100" />
				</el-form-item>
                <el-form-item label="认领状态" prop="status">
                    <el-select
                        v-model="modifyForm.status"
                        placeholder="请选择认领状态"
                    >
                        <el-option
                            v-for="item in statusList" :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        >
                            <el-tag
                                :type="item.value == 0 ? 'info': 'success'"
                            >
                                {{ item.value == 0 ? '待认领': '已认领' }}
                            </el-tag>
                        </el-option>
                    </el-select>
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

<script setup lang="ts" name="users">
import { reactive, ref } from 'vue';
import { Delete, Edit, Plus, Search, RefreshLeft, Download } from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus';
import type { Lost, ResponseData } from '@/utils/interface';
import { apiLostDelete, apiLostPageQuery, apiLostPost, apiLostPut, apiUploadLost } from '@/utils/api';
import { deepCopy } from '@/utils/copy';
import ImageUpload from '@/components/ImageUpload.vue';
import useGlobalStore from '@/stores/global';
import { exportExcel } from '@/utils/excel';


interface TableItem {
	id: number;
    describe: string;
    imgUrl: string;
    address: string;
    pickDatetime: string;
    claimDatetime: string;
    status: number;
}

const globalStore = useGlobalStore();
const query = reactive({
	pageIndex: 1,
	pageSize: 10,
	query: '',
    startDatetime: '',
    endDatetime: '',
});
const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
// 获取表格数据
const getData = () => {
	apiLostPageQuery(query, (data: ResponseData) => {
        ElMessage.success('失物招领数据获取成功');
		tableData.value = data.data.list;
		pageTotal.value = data.data.total || 50;
    }, (data: ResponseData) => {
        ElMessage.warning('失物招领数据获取失败');
    });
};
getData();

// 多选操作
const multipleSelection = ref<TableItem[]>([]);
const handleSelectionChange = (val: TableItem[]) => {
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
        apiLostDelete(multipleSelection.value.map(item => item.id), () => {
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
	ElMessageBox.confirm(`确定要删除 "${row.describe.substring(0, 8)}..." 吗？`, '提示', {
		type: 'warning'
	})
    .then(() => {
        apiLostDelete(row.id, () => {
            ElMessage.success('删除成功');
            getData();
        });
    })
    .catch(() => {});
};

const uploadHandler = (base64: string, hideDialog: Function) => {
    apiUploadLost(base64, (data: ResponseData) => {
        if (addVisible.value) addForm.imgUrl = data.data;
        if (modifyVisible.value) modifyForm.imgUrl = data.data;

        hideDialog();
    });
};

// 状态列表
const statusList = reactive([
    {value: 0, label: '待认领'},
    {value: 1, label: '已认领'},
]);

// 添加表格验证
const addRules: FormRules = {
	describe: [{ required: true, message: '请输入描述', trigger: 'blur' }],
	address: [{ required: true, message: '请输入拾取地址', trigger: 'blur' }],
	pickDatetime: [{ required: true, message: '请输入拾取时间', trigger: 'blur' }],
};
// 表格添加时弹窗和保存
const addVisible = ref(false);
const addFormRef = ref<FormInstance>();
const addForm = reactive<Lost>({
    imgUrl: '',
    status: 0,
});
const handleAdd = () => {
	addVisible.value = true;
};
const saveAdd = (formEl: FormInstance | undefined) => {
    formEl && formEl.validate((valid: boolean) => {
        if (!valid) return;

        apiLostPut(addForm, (data: ResponseData) => {
            ElMessage.success(data.message);
            addVisible.value = false;
            getData();
        });
    });
};
const exportXlsx = () => {
    const list = tableData.value.map(item => [item.id, item.describe, item.address, item.imgUrl, item.pickDatetime, item.claimDatetime, item.status]);

    list.unshift(['ID', '描述', '拾取地点', '图片', '拾取时间', '认领时间', '认领状态']);

    exportExcel(`失物招领_第${query.pageIndex}页_${new Date().valueOf()}`, list);
};

// 修改表格验证
const modifyRules: FormRules = {
};
// 表格修改时弹窗和保存
const modifyVisible = ref(false);
const modifyFormRef = ref<FormInstance>();
type LostExtra = Lost & { username?: string };
const modifyForm = reactive<LostExtra>({
    describe: undefined,
    address: undefined,
    status: undefined,
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
        apiLostPost(modifyForm, (data: ResponseData) => {
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
    width: 150px;
    height: 100px;
}
.info-image {
    position: relative;
    width: 150px;
    height: 100px;
}
.info-edit {
	display: flex;
	justify-content: center;
	align-items: center;
	position: absolute;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.5);
	opacity: 0;
	transition: opacity 0.3s ease;
}
.info-edit i {
	color: #eee;
	font-size: 25px;
}
.info-image:hover .info-edit {
	opacity: 1;
}
</style>
