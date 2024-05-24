<template>
	<div>
		<div class="container">
            <div class="handle-box">
                <el-button type="danger" :icon="Delete" @click="deleteBatch()">批量删除</el-button>
                <el-button type="primary" :icon="Plus" @click="handleAdd()">新增</el-button>
                <el-button type="primary" :icon="Download" @click="exportXlsx()">导出Excel</el-button>
                <el-button :icon="RefreshLeft" @click="getData()">刷新</el-button>

                <div class="float-end">
                    <el-input v-model="query.query" @change="handleSearch()" prefix-icon="Search" clearable placeholder="模糊搜索" class="handle-input mr10"></el-input>
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
				<el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
				<el-table-column prop="name" label="英文名"></el-table-column>
				<el-table-column prop="nameZh" label="中文名"></el-table-column>

				<el-table-column label="操作" width="220" align="center">
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
		</div>

		<!-- 添加弹出框 -->
		<el-dialog title="添加" v-model="addVisible" width="30%">
			<el-form :model="addForm" ref="addFormRef" :rules="addRules" label-width="70px">
				<el-form-item label="英文名" prop="name">
					<el-input v-model="addForm.name"></el-input>
				</el-form-item>
				<el-form-item label="中文名" prop="nameZh">
                    <el-input v-model="addForm.nameZh"></el-input>
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
                <el-form-item label="英文名" prop="name">
                    <el-input v-model="modifyForm.name"></el-input>
                </el-form-item>
                <el-form-item label="中文名" prop="nameZh">
                    <el-input v-model="modifyForm.nameZh"></el-input>
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
import { Delete, Edit, Plus, Search, RefreshLeft, Download } from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus';
import { reactive, ref } from 'vue';
import { apiRolePageQuery, apiRolePut, apiRolePost, apiRoleDelete } from '@/utils/api';
import { deepCopy } from '@/utils/copy';
import type { PageQuery, ResponseData } from '@/utils/interface';
import { exportExcel } from '@/utils/excel';

interface TableItem {
	id: number;
	name: string;
	nameZh: string;
}

const query = ref<PageQuery>({
    pageIndex: 1,
    pageSize: 30,
    query: '',
});
const tableData = ref<TableItem[]>([]);
// 获取表格数据
const getData = () => {
	apiRolePageQuery(query.value, (data: ResponseData) => {
        ElMessage.success('角色数据获取成功');
		tableData.value = data.data.list;
    }, (data: ResponseData) => {
        ElMessage.warning('角色数据获取失败');
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
    if (!multipleSelection.value.length) ElMessage.warning('未选择任何数据');

	// 二次确认删除
	ElMessageBox.confirm(`确定要删除 "${multipleSelection.value.length}" 条数据吗？`, '提示', {
		type: 'warning'
	})
		.then(() => {
            apiRoleDelete(multipleSelection.value.map(item => item.id), () => {
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

	getData();
};
// 删除操作
const handleDelete = (index: number, row: any) => {
	// 二次确认删除
	ElMessageBox.confirm(`确定要删除角色 "${row.nameZh}" 吗？`, '提示', {
		type: 'warning'
	})
		.then(() => {
            apiRoleDelete(row.id, () => {
                ElMessage.success('删除成功');
                getData();
            });
		})
		.catch(() => {});
};

// 添加表格验证
const addRules: FormRules = {
	name: [{ required: true, message: '请输入英文名', trigger: 'blur' }],
	nameZh: [{ required: true, message: '请输入中文名', trigger: 'blur' }],
};
// 表格添加时弹窗和保存
const addVisible = ref(false);
const addFormRef = ref<FormInstance>();
const addForm = reactive({
    name: '',
    nameZh: '',
});
const handleAdd = () => {
	addVisible.value = true;
};
const saveAdd = (formEl: FormInstance | undefined) => {
    formEl && formEl.validate((valid: boolean) => {
        if(!valid) return;

        apiRolePut(addForm, (data: ResponseData) => {
            getData();
            addVisible.value = false;
            ElMessage.success(data.message);
        });
    });
};
const exportXlsx = () => {
    const list = tableData.value.map(item => [item.id, item.name, item.nameZh]);

    list.unshift(['ID', '角色英文名', '角色中文名']);

    exportExcel(`角色_第${1}页_${new Date().valueOf()}`, list);
};

const modifyRules: FormRules = {
	name: [{ required: true, message: '请输入英文名', trigger: 'blur' }],
	nameZh: [{ required: true, message: '请输入中文名', trigger: 'blur' }],
};
// 表格修改时弹窗和保存
const modifyVisible = ref(false);
const modifyFormRef = ref<FormInstance>();
const modifyForm = reactive({
    id: null as unknown as number,
    name: '',
    nameZh: '',
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
        apiRolePost(modifyForm, (data: ResponseData) => {
            getData();
            modifyVisible.value = false;
            ElMessage.success(`修改成功 (ID ${modifyForm.id})`);
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
	width: 230px;
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
