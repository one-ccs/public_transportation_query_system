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
				<el-table-column prop="username" label="发布用户" width="125"></el-table-column>
				<el-table-column prop="title" label="标题"></el-table-column>
				<el-table-column prop="content" label="内容" min-width="200" show-overflow-tooltip></el-table-column>
				<el-table-column label="状态" width="88" align="center">
					<template #default="scope">
						<el-tag
							:type="scope.row.status == 0 ? 'info': 'success'"
						>
							{{ scope.row.status == 0 ? '不显示': '显示' }}
						</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="releaseDatetime" label="发布日期" align="center" sortable></el-table-column>

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
				<el-form-item label="标题" prop="title">
					<el-input v-model="addForm.title"></el-input>
				</el-form-item>
				<el-form-item label="内容" prop="content">
                    <el-input v-model="addForm.content"></el-input>
				</el-form-item>
                <el-form-item label="状态" prop="status">
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
                                {{ item.value == 0 ? '不显示': '显示' }}
                            </el-tag>
                        </el-option>
                    </el-select>
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
				<el-form-item label="发布用户" prop="username">
					<el-input v-model="modifyForm.username" disabled></el-input>
				</el-form-item>
				<el-form-item label="标题" prop="title">
					<el-input v-model="modifyForm.title"></el-input>
				</el-form-item>
				<el-form-item label="内容" prop="content">
					<el-input v-model="modifyForm.content"></el-input>
				</el-form-item>
                <el-form-item label="状态" prop="status">
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
                                {{ item.value == 0 ? '不显示': '显示' }}
                            </el-tag>
                        </el-option>
                    </el-select>
                </el-form-item>
				<el-form-item label="发布日期" prop="releaseDatetime">
					<el-input v-model="modifyForm.releaseDatetime" disabled></el-input>
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
import type { Notice, ResponseData } from '@/utils/interface';
import { apiNoticeDelete, apiNoticePageQuery, apiNoticePost, apiNoticePut } from '@/utils/api';
import { deepCopy } from '@/utils/copy';
import { exportExcel } from '@/utils/excel';


interface TableItem {
	id: number;
	username: string;
	title: string;
	content: string;
	status: number;
	releaseDatetime: string;
}

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
	apiNoticePageQuery(query, (data: ResponseData) => {
        ElMessage.success('公告数据获取成功');
		tableData.value = data.data.list;
		pageTotal.value = data.data.total || 50;
    }, (data: ResponseData) => {
        ElMessage.warning('公告数据获取失败');
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
        apiNoticeDelete(multipleSelection.value.map(item => item.id), () => {
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
	ElMessageBox.confirm(`确定要删除 "${row.title}" 吗？`, '提示', {
		type: 'warning'
	})
    .then(() => {
        apiNoticeDelete(row.id, () => {
            ElMessage.success('删除成功');
            getData();
        });
    })
    .catch(() => {});
};

// 状态列表
const statusList = reactive([
    {value: 0, label: '不显示'},
    {value: 1, label: '显示'},
]);

// 添加表格验证
const addRules: FormRules = {
	content: [{ required: true, message: '请输入公告内容', trigger: 'blur' }],
};
// 表格添加时弹窗和保存
const addVisible = ref(false);
const addFormRef = ref<FormInstance>();
const addForm = reactive<Notice>({
    status: 1,
});
const handleAdd = () => {
	addVisible.value = true;
};
const saveAdd = (formEl: FormInstance | undefined) => {
    formEl && formEl.validate((valid: boolean) => {
        if (!valid) return;

        apiNoticePut(addForm, (data: ResponseData) => {
            ElMessage.success(data.message);
            addVisible.value = false;
            getData();
        });
    });
};
const exportXlsx = () => {
    const list = tableData.value.map(item => [item.id, item.username, item.title, item.content, item.status, item.releaseDatetime]);

    list.unshift(['ID', '发布用户', '标题', '内容', '状态', '发布日期']);

    exportExcel(`线路_第${query.pageIndex}页_${new Date().valueOf()}`, list);
};

// 修改表格验证
const modifyRules: FormRules = {
	content: [{ required: true, message: '请输入公告内容', trigger: 'blur' }],
};
// 表格修改时弹窗和保存
const modifyVisible = ref(false);
const modifyFormRef = ref<FormInstance>();
type NoticeExtra = Notice & { username?: string };
const modifyForm = reactive<NoticeExtra>({
    title: undefined,
    content: undefined,
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
        apiNoticePost(modifyForm, (data: ResponseData) => {
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
