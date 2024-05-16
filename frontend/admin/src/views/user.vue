<template>
	<div>
		<el-row :gutter="20">
			<el-col :span="12">
				<el-card shadow="hover">
					<template #header>
						<div class="clearfix">
							<span>基础信息</span>
						</div>
					</template>
					<div class="info">
                        <image-upload
                            :image-src="userStore.fullAvatar"
                            :upload-handler="saveAvatar"
                            :width="100"
                            :height="100"
                            round />
						<div class="info-name">{{ name }}</div>
						<div class="info-desc">不可能！我的代码怎么可能会有bug！</div>
					</div>
				</el-card>
			</el-col>
			<el-col :span="12">
				<el-card shadow="hover">
					<template #header>
						<div class="clearfix">
							<span>账户编辑</span>
						</div>
					</template>
					<el-form label-width="90px">
						<el-form-item label="用户名："> {{ name }} </el-form-item>
						<el-form-item label="旧密码：">
							<el-input type="password" v-model="form.old"></el-input>
						</el-form-item>
						<el-form-item label="新密码：">
							<el-input type="password" v-model="form.new"></el-input>
						</el-form-item>
						<el-form-item label="个人简介：">
							<el-input v-model="form.desc"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" @click="onSubmit">保存</el-button>
						</el-form-item>
					</el-form>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script setup lang="ts" name="user">
import { reactive} from 'vue';
import { ElMessage } from 'element-plus';
import { apiUploadAvatar, apiUserModifyAvatar } from '@/utils/api';
import type { ResponseData } from '@/utils/interface';
import useUserStore from '@/stores/user';
import ImageUpload from '@/components/ImageUpload.vue';

const userStore = useUserStore();
const name = localStorage.getItem('ms_username');
const form = reactive({
	old: '',
	new: '',
	desc: '不可能！我的代码怎么可能会有bug！'
});
const onSubmit = () => {};

const saveAvatar = (base64: string, hideDialog: Function) => {
    apiUploadAvatar(base64, (data: ResponseData) => {

        apiUserModifyAvatar({
            id: userStore.userInfo.id!,
            filename: data.data,
        }, () => {
            ElMessage.success('头像修改成功');
            hideDialog();
            userStore.userInfo.avatar = data.data;
            userStore.save();
        });
    });
};
</script>

<style scoped>
.info {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
	padding: 35px 0;
}
.info-image {
	position: relative;
	margin: auto;
	width: 100px;
	height: 100px;
	background: #f8f8f8;
	border: 1px solid #eee;
	border-radius: 50px;
	overflow: hidden;
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
.info-name {
	margin: 15px 0 10px;
	font-size: 24px;
	font-weight: 500;
	color: #262626;
}
.crop-demo-btn {
	position: relative;
}
.crop-input {
	position: absolute;
	width: 100px;
	height: 40px;
	left: 0;
	top: 0;
	opacity: 0;
	cursor: pointer;
}
</style>
