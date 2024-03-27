<template>
	<div class="container">
		<h2>使用方法</h2>
		<p style="line-height: 50px">
			通过 el-icon 组件使用。例如：（共{{ iconList.length }}个图标）
		</p>
		<p class="example-p">
            <el-icon color="#ffc300" size="30">
                <Aim />
            </el-icon>
			<span>&lt;el-icon color="#ffc300" size="30"&gt;&lt;Aim /&gt;&lt;/el-icon&gt;</span>
		</p>
		<p class="example-p">
            <el-icon color="#fd5656" size="30">
                <component is="AddLocation" />
            </el-icon>
            <span>&lt;el-icon color="#fd5656" size="30"&gt;&lt;component is="AddLocation" /&gt;&lt;/el-icon&gt;</span>
		</p>
		<br />
		<h2>图标 2</h2>
		<div class="search-box">
			<el-input class="search" size="large" v-model="keyword" clearable placeholder="请输入图标名称"></el-input>
		</div>
		<ul>
			<li class="icon-li" v-for="(item, index) in list" :key="index">
				<div class="icon-li-content">
                    <el-icon>
                        <component :is="item" />
                    </el-icon>
                    <span>{{ item }}</span>
				</div>
			</li>
		</ul>
	</div>
</template>

<script setup lang="ts" name="icon">
import { computed, ref } from 'vue';

const iconList: string[] = [
  'AddLocation',
  'Aim',
  'AlarmClock',
  'Apple',
  'ArrowDown',
  'ArrowDownBold',
  'ArrowLeft',
  'ArrowLeftBold',
  'ArrowRight',
  'ArrowRightBold',
  'ArrowUp',
  'ArrowUpBold',
  'Avatar',
  'Back',
  'Baseball',
  'Basketball',
  'Bell',
  'BellFilled',
  'Bicycle',
  'Bottom',
  'BottomLeft',
  'BottomRight',
  'Bowl',
  'Box',
  'Briefcase',
  'Brush',
  'BrushFilled',
  'Burger',
  'Calendar',
  'Camera',
  'CameraFilled',
  'CaretBottom',
  'CaretLeft',
  'CaretRight',
  'CaretTop',
  'Cellphone',
  'ChatDotRound',
  'ChatDotSquare',
  'ChatLineRound',
  'ChatLineSquare',
  'ChatRound',
  'ChatSquare',
  'Check',
  'Checked',
  'Cherry',
  'Chicken',
  'ChromeFilled',
  'CircleCheck',
  'CircleCheckFilled',
  'CircleClose',
  'CircleCloseFilled',
  'CirclePlus',
  'CirclePlusFilled',
  'Clock',
  'Close',
  'CloseBold',
  'Cloudy',
  'Coffee',
  'CoffeeCup',
  'Coin',
  'ColdDrink',
  'Collection',
  'CollectionTag',
  'Comment',
  'Compass',
  'Connection',
  'Coordinate',
  'CopyDocument',
  'Cpu',
  'CreditCard',
  'Crop',
  'DArrowLeft',
  'DArrowRight',
  'DCaret',
  'DataAnalysis',
  'DataBoard',
  'DataLine',
  'Delete',
  'DeleteFilled',
  'DeleteLocation',
  'Dessert',
  'Discount',
  'Dish',
  'DishDot',
  'Document',
  'DocumentAdd',
  'DocumentChecked',
  'DocumentCopy',
  'DocumentDelete',
  'DocumentRemove',
  'Download',
  'Drizzling',
  'Edit',
  'EditPen',
  'Eleme',
  'ElemeFilled',
  'ElementPlus',
  'Expand',
  'Failed',
  'Female',
  'Files',
  'Film',
  'Filter',
  'Finished',
  'FirstAidKit',
  'Flag',
  'Fold',
  'Folder',
  'FolderAdd',
  'FolderChecked',
  'FolderDelete',
  'FolderOpened',
  'FolderRemove',
  'Food',
  'Football',
  'ForkSpoon',
  'Fries',
  'FullScreen',
  'Goblet',
  'GobletFull',
  'GobletSquare',
  'GobletSquareFull',
  'GoldMedal',
  'Goods',
  'GoodsFilled',
  'Grape',
  'Grid',
  'Guide',
  'Handbag',
  'Headset',
  'Help',
  'HelpFilled',
  'Hide',
  'Histogram',
  'HomeFilled',
  'HotWater',
  'House',
  'IceCream',
  'IceCreamRound',
  'IceCreamSquare',
  'IceDrink',
  'IceTea',
  'InfoFilled',
  'Iphone',
  'Key',
  'KnifeFork',
  'Lightning',
  'Link',
  'List',
  'Loading',
  'Location',
  'LocationFilled',
  'LocationInformation',
  'Lock',
  'Lollipop',
  'MagicStick',
  'Magnet',
  'Male',
  'Management',
  'MapLocation',
  'Medal',
  'Memo',
  'Menu',
  'Message',
  'MessageBox',
  'Mic',
  'Microphone',
  'MilkTea',
  'Minus',
  'Money',
  'Monitor',
  'Moon',
  'MoonNight',
  'More',
  'MoreFilled',
  'MostlyCloudy',
  'Mouse',
  'Mug',
  'Mute',
  'MuteNotification',
  'NoSmoking',
  'Notebook',
  'Notification',
  'Odometer',
  'OfficeBuilding',
  'Open',
  'Operation',
  'Opportunity',
  'Orange',
  'Paperclip',
  'PartlyCloudy',
  'Pear',
  'Phone',
  'PhoneFilled',
  'Picture',
  'PictureFilled',
  'PictureRounded',
  'PieChart',
  'Place',
  'Platform',
  'Plus',
  'Pointer',
  'Position',
  'Postcard',
  'Pouring',
  'Present',
  'PriceTag',
  'Printer',
  'Promotion',
  'QuartzWatch',
  'QuestionFilled',
  'Rank',
  'Reading',
  'ReadingLamp',
  'Refresh',
  'RefreshLeft',
  'RefreshRight',
  'Refrigerator',
  'Remove',
  'RemoveFilled',
  'Right',
  'ScaleToOriginal',
  'School',
  'Scissor',
  'Search',
  'Select',
  'Sell',
  'SemiSelect',
  'Service',
  'SetUp',
  'Setting',
  'Share',
  'Ship',
  'Shop',
  'ShoppingBag',
  'ShoppingCart',
  'ShoppingCartFull',
  'ShoppingTrolley',
  'Smoking',
  'Soccer',
  'SoldOut',
  'Sort',
  'SortDown',
  'SortUp',
  'Stamp',
  'Star',
  'StarFilled',
  'Stopwatch',
  'SuccessFilled',
  'Sugar',
  'Suitcase',
  'SuitcaseLine',
  'Sunny',
  'Sunrise',
  'Sunset',
  'Switch',
  'SwitchButton',
  'SwitchFilled',
  'TakeawayBox',
  'Ticket',
  'Tickets',
  'Timer',
  'ToiletPaper',
  'Tools',
  'Top',
  'TopLeft',
  'TopRight',
  'TrendCharts',
  'Trophy',
  'TrophyBase',
  'TurnOff',
  'Umbrella',
  'Unlock',
  'Upload',
  'UploadFilled',
  'User',
  'UserFilled',
  'Van',
  'VideoCamera',
  'VideoCameraFilled',
  'VideoPause',
  'VideoPlay',
  'View',
  'Wallet',
  'WalletFilled',
  'WarnTriangleFilled',
  'Warning',
  'WarningFilled',
  'Watch',
  'Watermelon',
  'WindPower',
  'ZoomIn',
  'ZoomOut'
];
const keyword = ref('');
const list = computed(() => {
	return iconList.filter(item => {
		return item.toLowerCase().includes(keyword.value.toLowerCase());
	});
});
</script>

<style scoped>
.example-p {
	height: 45px;
	display: flex;
	align-items: center;
}
.search-box {
	text-align: center;
	margin-top: 10px;
}
.search {
	width: 300px;
}
ul,
li {
	list-style: none;
}
.icon-li {
	display: inline-block;
	padding: 10px;
	width: 120px;
	height: 120px;
}
.icon-li-content {
	display: flex;
	height: 100%;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	cursor: pointer;
}
.icon-li-content i {
	font-size: 36px;
	color: #606266;
}
.icon-li-content span {
	margin-top: 10px;
	color: #787878;
}
</style>
