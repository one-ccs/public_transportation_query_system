<template>
	<div class="container">
		<div class="mgb20">
			<span class="label">角色：</span>
			<el-select v-model="userStore.userInfo.roles[0]" @change="handleChange">
				<el-option label="超级管理员" value="superAdmin"></el-option>
				<el-option label="管理员" value="admin"></el-option>
				<el-option label="用户管理员" value="userAdmin"></el-option>
			</el-select>
		</div>
		<div class="mgb20 tree-wrapper">
			<el-tree
				ref="tree"
				:data="data"
				node-key="id"
				default-expand-all
				show-checkbox
				:default-checked-keys="checkedKeys"
			/>
		</div>
		<el-button type="primary" @click="onSubmit">保存权限</el-button>
	</div>
</template>

<script setup lang="ts" name="permission">
import { ref } from 'vue';
import { ElTree } from 'element-plus';
import usePermissStore from '@/stores/permiss';
import useUserStore from '@/stores/user';

const userStore = useUserStore();

interface Tree {
	id: string;
	label: string;
	children?: Tree[];
}

const data: Tree[] = [
	{
		id: '1',
		label: '系统首页'
	},
    {
        id: '2',
        label: '用户管理',
        children: [
            {
                id: '2.1',
                label: '普通用户管理',
            },
            {
                id: '2.2',
                label: '管理员管理',
            },
            {
                id: '2.3',
                label: '角色管理',
            },
        ],
    },
    {
        id: '3',
        label: '线路管理',
    },
    {
        id: '4',
        label: '站点管理',
    },
    {
        id: '5',
        label: '公告管理',
    },
    {
        id: '6',
        label: '失物招领管理',
    },
	{
		id: '7',
		label: '基础表格',
		children: [
			{
				id: '15',
				label: '编辑'
			},
			{
				id: '16',
				label: '删除'
			}
		]
	},
	{
		id: '8',
		label: 'tab选项卡'
	},
	{
		id: '9',
		label: '表单相关',
		children: [
			{
				id: '5',
				label: '基本表单'
			},
			{
				id: '6',
				label: '文件上传'
			},
			{
				id: '7',
				label: '三级菜单',
				children: [
					{
						id: '8',
						label: '富文本编辑器'
					},
					{
						id: '9',
						label: 'markdown编辑器'
					}
				]
			}
		]
	},
	{
		id: '10',
		label: '自定义图标'
	},
	{
		id: '11',
		label: 'schart图表'
	},

	{
		id: '13',
		label: '权限管理'
	},
];

const permiss = usePermissStore();

// 获取当前权限
const checkedKeys = ref<string[]>([]);
const getPremission = () => {
	checkedKeys.value = permiss.getPermiss();
};
getPremission();

// 保存权限
const tree = ref<InstanceType<typeof ElTree>>();
const onSubmit = () => {
	// 获取选中的权限
	console.log(tree.value!.getCheckedKeys(false));
    localStorage.setItem('ms_keys', JSON.stringify(tree.value!.getCheckedKeys(false)));
};

const handleChange = (val: string) => {
	tree.value!.setCheckedKeys(permiss.defaultList[val]);
};
</script>

<style scoped>
.tree-wrapper {
	max-width: 500px;
}
.label {
	font-size: 14px;
}
</style>
