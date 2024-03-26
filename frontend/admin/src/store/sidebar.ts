import { defineStore } from 'pinia';

const useSidebarStore = defineStore('sidebar', {
	state: () => {
		return {
			collapse: false
		};
	},
	getters: {},
	actions: {
		handleCollapse() {
			this.collapse = !this.collapse;
		}
	}
});

export default useSidebarStore;
