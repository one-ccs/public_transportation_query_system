const map = {
    zh: {
        'user': '用户',
        'admin': '管理员',
        'userAdmin': '用户管理员',
        'systemAdmin': '系统管理员',
        'superAdmin': '超级管理员',
    },
    serialNumber: {
        1:  '①',
        2:  '②',
        3:  '③',
        4:  '④',
        5:  '⑤',
        6:  '⑥',
        7:  '⑦',
        8:  '⑧',
        9:  '⑨',
        10: '⑩',
        11: '⑪',
        12: '⑫',
        13: '⑬',
        14: '⑭',
        15: '⑮',
        16: '⑯',
        17: '⑰',
        18: '⑱',
        19: '⑲',
        20: '⑳',
        21: '㉑',
        22: '㉒',
        23: '㉓',
        24: '㉔',
        25: '㉕',
        26: '㉖',
        27: '㉗',
        28: '㉘',
        29: '㉙',
        30: '㉚',
        31: '㉛',
        32: '㉜',
        33: '㉝',
        34: '㉞',
        35: '㉟',
        36: '㊱',
        37: '㊲',
        38: '㊳',
        39: '㊴',
        40: '㊵',
        41: '㊶',
        42: '㊷',
        43: '㊸',
        44: '㊹',
        45: '㊺',
        46: '㊻',
        47: '㊼',
        48: '㊽',
        49: '㊾',
        50: '㊿',
    },
};


/**
 * 返回 map 路径中查找到的值, 没有返回默认值
 * @param key 键名
 * @param path 路径 (以英文句号 . 分隔)
 * @param _default 默认值
 * @returns
 */
export default function(key: string | number, path?: string, _default: any = null) {
    if (path) key = `${path}.${key}`;
    return String(key).split('.').reduce((o: any, i: string) => o && o[i], map) || _default;
}
