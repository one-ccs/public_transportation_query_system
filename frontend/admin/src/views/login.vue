<template>
	<div class="login-wrap">
		<div class="ms-login">
			<div class="ms-title">重庆公交查询后台管理系统</div>
			<el-form :model="loginForm" :rules="rules" ref="loginFormRef" label-width="0px" class="ms-content">
				<el-form-item prop="username">
					<el-input v-model="loginForm.username" placeholder="username">
						<template #prepend>
							<el-button :icon="User"></el-button>
						</template>
					</el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input
						type="password"
						placeholder="password"
						v-model="loginForm.password"
						@keyup.enter="submitForm(loginFormRef)"
					>
						<template #prepend>
							<el-button :icon="Lock"></el-button>
						</template>
					</el-input>
				</el-form-item>
                <el-form-item>
                    <el-checkbox v-model="loginForm.remember">记住我</el-checkbox>
                </el-form-item>
				<div class="login-btn">
					<el-button type="primary" @click="submitForm(loginFormRef)">登录</el-button>
				</div>
			</el-form>
		</div>
	</div>
</template>

<script setup lang="ts">
import type { FormInstance, FormRules } from 'element-plus';
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { Lock, User } from '@element-plus/icons-vue';
import type { ResponseData, UserLogin } from '@/utils/interface';
import { apiLogin } from '@/utils/api';
import useTagsStore from '@/stores/tags';
import usePermissStore from '@/stores/permiss';
import useUserStore from '@/stores/user';


const router = useRouter();
const loginForm = reactive<UserLogin>({
	username: 'admin',
	password: '',
    remember: false,
});

const rules: FormRules = {
	username: [
		{
			required: true,
			message: '请输入用户名',
			trigger: 'blur'
		}
	],
	password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
};
const permiss = usePermissStore();
const userStore = useUserStore();
const loginFormRef = ref<FormInstance>();
const submitForm = (formEl: FormInstance | undefined) => {
    formEl && formEl.validate(async (valid: boolean) => {
        if (!valid) return;

        apiLogin(loginForm, (data: ResponseData) => {
            if (data.data.roles.includes('user') && data.data.roles.length === 1) {
                return ElMessage.warning('您无权登录该系统！');
            }

            ElMessage.success('登录成功');
            userStore.setUser(data.data);
            localStorage.setItem('ms_username', loginForm.username);
            const keys = permiss.defaultList[loginForm.username == 'admin' ? 'admin' : 'user'];
            permiss.handleSet(keys);
            localStorage.setItem('ms_keys', JSON.stringify(keys));
            router.push('/');
        });
    });
};

const tags = useTagsStore();
tags.clearTags();
</script>

<style scoped>
.login-wrap {
	position: relative;
	width: 100%;
	height: 100%;
	background-image: url(@/assets/img/login-bg.jpg);
	background-size: cover;
    background-position: center center;
    background-repeat: no-repeat;
}
.login-wrap::after {
    content: "";
    z-index: 1;
    position: absolute;
    left: 0;
    top: 0;
    background: #0005;
    width: 100%;
    height: 100%;
    backdrop-filter: blur(3px);
}
.ms-title {
	width: 100%;
	line-height: 50px;
	text-align: center;
	font-size: 20px;
	color: #fff;
	border-bottom: 1px solid #ddd;
}
.ms-login {
    z-index: 2;
	position: absolute;
	left: 50%;
	top: 50%;
	width: 350px;
	margin: -190px 0 0 -175px;
	border-radius: 5px;
	background: rgba(255, 255, 255, 0.3);
	overflow: hidden;
}
.ms-content {
	padding: 30px 30px;
}
.login-btn {
	text-align: center;
}
.login-btn button {
	width: 100%;
	height: 36px;
	margin-bottom: 10px;
}
.login-tips {
	font-size: 12px;
	line-height: 30px;
	color: #fff;
}
</style>
