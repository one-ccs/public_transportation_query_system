<template>
    <div class="image-upload" >
        <div class="image-upload-box" @click="showDialog()" :style="{ width: _width, height: _height }">
            <el-image class="image-wrapper" fit="cover" :src="src">
                <template #error>
                    <div class="image-slot">
                        <el-icon><Picture /></el-icon>
                    </div>
                </template>
            </el-image>
        </div>

        <el-dialog title="裁剪图片" v-model="dialogVisible" width="600px">
            <vue-cropper
                ref="cropperRef"
                :src="src || logoPng"
                :ready="cropperImage()"
                :zoom="cropperImage()"
                :cropmove="cropperImage()"
                style="width: 100%; height: 380px"
            ></vue-cropper>

            <template #footer>
                <span class="dialog-footer">
                    <el-button class="crop-demo-btn" type="primary">选择图片
                        <input class="crop-input" type="file" name="image" accept="image/*" @change="readImage" />
                    </el-button>
                    <el-button type="primary" @click="onUploadClick()">上传</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue';
import VueCropper from 'vue-cropperjs';
import 'cropperjs/dist/cropper.css';
import logoPng from '@/assets/img/logo.png';

const src = defineModel({ type: String, required: true });

const props = withDefaults(defineProps<{
    width?: number | string;
    height?: number | string;
    upload?: Function;
}>(), {
    width: '64px',
    height: '64px',
});

const emit = defineEmits<{
    (e: "nodeClick", event: MouseEvent, data: any, nodeElement: HTMLElement): void,
}>();

const _height = computed(() => {
    if (typeof props.height === "number") return props.height + "px";
    return props.height;
});
const _width = computed(() => {
    if (typeof props.width === "number") return props.width + "px";
    return props.width;
});


// 裁剪图片弹窗
const dialogVisible = ref(false);
const cropperRef = ref();
let cropperBase64 = '';

const showDialog = () => {
	dialogVisible.value = true;
};
const hideDialog = () => {
    dialogVisible.value = false;
};
const cropperImage = () => {
	cropperBase64 = cropperRef.value.getCroppedCanvas().toDataURL();
};
const readImage = (e: any) => {
    const file = e.target.files[0];

	if (!file || !file.type.includes('image/')) return;

	const reader = new FileReader();
	reader.onload = (event: any) => {
		dialogVisible.value = true;
		cropperBase64 = event.target.result;
		cropperRef.value.replace(event.target.result);
	};
	reader.readAsDataURL(file);
};
const onUploadClick = () => {
    props.upload ? props.upload(hideDialog) : hideDialog();
};
</script>

<style scoped>
.image-wrapper {
    width: 100%;
    height: 100%;
}
.image-slot {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  font-size: 38px;
  color: #999;
  background: #0001;
}
.image-slot:hover {
    color: #888;
    background: #0002;
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
