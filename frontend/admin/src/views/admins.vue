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
                    <el-input v-model="query.query" @change="handleSearch()" prefix-icon="Search" clearable placeholder="搜索用户名或邮箱地址" class="handle-input mr10"></el-input>
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
				<el-table-column prop="username" label="用户名" width="125" sortable></el-table-column>
				<el-table-column prop="password" label="密码" min-width="200" show-overflow-tooltip></el-table-column>
				<el-table-column prop="email" label="邮箱地址" show-overflow-tooltip></el-table-column>
				<el-table-column prop="registerDatetime" label="注册时间" width="160" align="center" sortable></el-table-column>
				<el-table-column label="状态" width="100" align="center">
					<template #default="scope">
						<el-tag
							:type="scope.row.status == 0 ? 'info': scope.row.status == 1 ? 'success': 'danger'"
						>
							{{ scope.row.status == 0 ? '未激活': scope.row.status == 1 ? '已激活': '已注销' }}
						</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="角色" align="center">
					<template #default="scope">
                        <el-tag class="me-1" v-for="(item, index) in scope.row.roles" :key="index">{{ item.nameZh }}</el-tag>
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
		<el-dialog title="添加" v-model="addVisible" width="30%">
			<el-form :model="addForm" ref="addFormRef" :rules="addRules" label-width="70px">
				<el-form-item label="用户名" prop="username">
					<el-input v-model="addForm.username"></el-input>
				</el-form-item>
				<el-form-item label="密码" prop="password">
                    <el-input v-model="addForm.password"></el-input>
				</el-form-item>
				<el-form-item label="确认密码" prop="passwordCheck">
					<el-input v-model="addForm.passwordCheck"></el-input>
				</el-form-item>
				<el-form-item label="邮箱地址" prop="email">
					<el-input v-model="addForm.email"></el-input>
				</el-form-item>
                <el-form-item label="角色" prop="roles">
                    <el-select
                        v-model="addForm.roles"
                        multiple
                        placeholder="请选择角色"
                        value-key="id"
                    >
                        <el-option
                            v-for="item in roleList" :key="item.id"
                            :label="item.nameZh"
                            :value="item"
                        />
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
				<el-form-item label="用户名" prop="username">
					<el-input v-model="modifyForm.username"></el-input>
				</el-form-item>
				<el-form-item label="密码" prop="password">
					<el-input v-model="modifyForm.password"></el-input>
				</el-form-item>
				<el-form-item label="确认密码" prop="passwordCheck">
					<el-input v-model="modifyForm.passwordCheck"></el-input>
				</el-form-item>
				<el-form-item label="邮箱地址" prop="email">
					<el-input v-model="modifyForm.email"></el-input>
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
                                :type="item.value == 0 ? 'info': item.value == 1 ? 'success': 'danger'"
                            >
                                {{ item.value == 0 ? '未激活': item.value == 1 ? '已激活': '已注销' }}
                            </el-tag>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="角色" prop="roles">
                    <el-select
                        v-model="modifyForm.roles"
                        multiple
                        placeholder="请选择角色"
                        value-key="id"
                    >
                        <el-option
                            v-for="item in roleList" :key="item.id"
                            :label="item.nameZh"
                            :value="item"
                        />
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
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus';
import { Delete, Edit, Plus, Search, RefreshLeft } from '@element-plus/icons-vue';
import type { ResponseData, Role, TimeRangePageQuery, UserVO } from '@/utils/interface';
import { apiUserPut, apiPageUser, apiUserPost, apiRolePageQuery, apiUserDelete } from '@/utils/api';
import { deepCopy } from '@/utils/copy';


interface TableItem {
	id: number;
	username: string;
	password: string;
	email: string;
	registerDatetime: string;
	status: number;
    roles: Object[];
}

const query = reactive({
	pageIndex: 1,
	pageSize: 10,
	query: '',
    startDatetime: '',
    endDatetime: '',
    filterFlag: 2
});
const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
// 获取表格数据
const getData = () => {
	apiPageUser(query, (data: ResponseData) => {
        ElMessage.success('管理员数据获取成功');
		tableData.value = data.data.list;
		pageTotal.value = data.data.total || 50;
    }, (data: ResponseData) => {
        ElMessage.warning('管理员数据获取失败');
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
            apiUserDelete(multipleSelection.value.map(item => item.id), () => {
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
	ElMessageBox.confirm(`确定要删除 "${row.username ? row.username : row.email}" 吗？`, '提示', {
		type: 'warning'
	})
		.then(() => {
            apiUserDelete(row.id, () => {
                ElMessage.success('删除成功');
                getData();
            });
		})
		.catch(() => {});
};

// 角色列表，需在线加载
const roleList = reactive<Role[]>([
    {id: 1, name: 'user', nameZh: '用户'},
]);
const statusList = reactive([
    {value: 0, label: '未激活'},
    {value: 1, label: '已激活'},
    {value: 2, label: '已注销'},
]);

// 添加用户密码验证
const validatePassAdd = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入密码'));
    } else {
        if (addForm.passwordCheck !== '') {
            if (!addFormRef.value) return;
            addFormRef.value.validateField('passwordCheck', () => null);
        }
        callback();
    }
};
const validatePassAdd2 = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请二次确认密码'));
    } else if (value !== addForm.password) {
        callback(new Error("二次确认密码不匹配"));
    } else {
        callback();
    }
};
// 添加表格验证
const addRules: FormRules = {
	username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { validator: validatePassAdd, trigger: 'blur' }
    ],
    passwordCheck: [{ validator: validatePassAdd2, trigger: 'blur' }],
    email: [{ type: 'email', message: '请输入正确的电子邮件地址', trigger: ['blur', 'change'] }],
    roles: [{ required: true, message: '请选择角色', trigger: 'blur' }],
};
// 表格添加时弹窗和保存
const addVisible = ref(false);
const addFormRef = ref<FormInstance>();
const addForm = reactive({
    username: '',
    password: '',
    passwordCheck: '',
    email: '',
    roles: [roleList[0]],
});
const handleAdd = () => {
    apiRolePageQuery({} as TimeRangePageQuery, (data: ResponseData) => {
        deepCopy(roleList, data.data.list);
    })
    addForm.passwordCheck = '';
	addVisible.value = true;
};
const saveAdd = (formEl: FormInstance | undefined) => {
    formEl && formEl.validate((valid: boolean) => {
        if (!valid) return;

        apiUserPut(addForm, (data: ResponseData) => {
            ElMessage.success(data.message);
            addVisible.value = false;
            getData();
        });
    });
};

// 修改用户密码验证
const validatePassModify = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入密码'));
    } else {
        if (modifyForm.passwordCheck !== '') {
            if (!modifyFormRef.value) return;
            modifyFormRef.value.validateField('passwordCheck', () => null);
        }
        callback();
    }
}
const validatePassModify2 = (rule: any, value: any, callback: any) => {
    if (modifyForm.password === oldPassword) callback();
    if (value === '') {
        callback(new Error('请二次确认密码'));
    } else if (value !== modifyForm.password) {
        callback(new Error("二次确认密码不匹配"));
    } else {
        callback();
    }
}
const modifyRules: FormRules = {
    password: [{ validator: validatePassModify, trigger: 'blur' }],
    passwordCheck: [{ validator: validatePassModify2, trigger: 'blur' }],
    email: [{ type: 'email', message: '请输入正确的电子邮件地址', trigger: ['blur', 'change'] }],
};
// 表格修改时弹窗和保存
const modifyVisible = ref(false);
const modifyFormRef = ref<FormInstance>();
const modifyForm = reactive<UserVO>({
    passwordCheck: '',
    passwordModified: false,
    roles: [roleList[0]],
});
let oldModifyString = '';
let oldPassword = '';
const handleModify = (row: any) => {
    oldPassword = row.password;
    modifyForm.passwordCheck = '';
    deepCopy(modifyForm, row);
    oldModifyString = JSON.stringify(modifyForm);

    apiRolePageQuery({} as TimeRangePageQuery, (data: ResponseData) => {
        deepCopy(roleList, data.data.list);
    });
	modifyVisible.value = true;
};
const saveModify = (formEl: FormInstance | undefined) => {
    formEl && formEl.validate((valid: boolean) => {
        if (!valid) return;

        // 表单未修改
        if (JSON.stringify(modifyForm) === oldModifyString) return ElMessage.warning('未修改');
        // 密码未修改
        if (modifyForm.password !== oldPassword) modifyForm.passwordModified = true;
        apiUserPost(modifyForm, (data: ResponseData) => {
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
