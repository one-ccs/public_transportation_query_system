import { defineStore } from 'pinia';
import useUserStore from '@/stores/user';
import pinia from './pinia';

const userStore = useUserStore(pinia);

interface ObjectList {
	[key: string]: string[];
}

const usePermissStore = defineStore('permiss', {
	state: () => {
		const keys = localStorage.getItem('ms_keys');
		return {
			key: keys ? JSON.parse(keys) : <string[]>[],
			defaultList: <ObjectList>{
				superAdmin: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16'],
				admin: ['1', '2', '3', '4', '5', '6', '15', '16'],
				userAdmin: ['1', '2', '15', '16'],
				systemAdmin: ['1', '3', '4', '5', '6', '15', '16'],
			}
		};
	},
	actions: {
		handleSet(val: string[]) {
			this.key = val;
		},
        getPermiss() {
            return this.key.length ? this.key : this.defaultList[userStore.userInfo.roles[0] || 'sysAdmin'];
        },
	}
});

export default usePermissStore;
