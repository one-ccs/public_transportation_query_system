import { isDate } from './typeof'


/**
 * 将日期类型按指定形式格式化为字符串
 *
 * @param {Date} datetime 待转换的日期
 * @param {string} format 转换格式 yMdhmsqS 分别表示年、月、日、时、分、秒、季度、毫秒
 * @returns 格式化后的字符串
 */
function strftime(datetime: Date, format: string='yyyy-MM-dd hh:mm:ss') {
    if(!isDate(datetime)) throw new TypeError('期望一个 [object Date] 类型')

    const o = <any>{
        'M+': datetime.getMonth() + 1,                    //月份
        'd+': datetime.getDate(),                         //日
        'h+': datetime.getHours(),                        //小时
        'm+': datetime.getMinutes(),                      //分
        's+': datetime.getSeconds(),                      //秒
        'q+': Math.floor((datetime.getMonth() + 3) / 3),  //季度
        'S': datetime.getMilliseconds()                   //毫秒
    }
    const yExec = /y+/.exec(format)

    if (yExec) {
        format = format.replace(yExec[0], datetime.getFullYear().toString().substring(4 - yExec[0].length))
    }
    for (let key in o) {
        const keyExec = (new RegExp(key)).exec(format)

        if (keyExec) {
            format = format.replace(keyExec[0], (keyExec[0].length === 1)? o[key]: (('00' + o[key]).substring(o[key].toString().length)))
        }
    }
    return format
}

export {
    strftime,
}
