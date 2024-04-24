import { getPrototype, isArray, isBasic, isObject, isPrimitive } from './typeof';


/**
 * 将 from 深拷贝到 to，使代理不丢失其响应性
 * @param to 拷贝到该对象
 * @param from 从该对象拷贝
 */
export function deepCopy(to: any, from: any) {
    if (!isArray(to) && !isObject(to))
        throw new TypeError(`参数 to(${getPrototype(to)}, ${to}) 必须是 Array 或 Object 类型`);
    if (!isArray(from) && !isObject(from))
        throw new TypeError(`参数 from(${getPrototype(from)}, ${from}) 必须是 Array 或 Object 类型`);
    if (getPrototype(to) !== getPrototype(from))
        throw new TypeError(`参数 to(${getPrototype(to)}, ${to}) 和 from(${getPrototype(from)}, ${from}) 必须是相同的数据类型`);

    for(let key of Object.keys(from)) {
        if (isBasic(from[key]) || from[key] === null) {
            to[key] = from[key];
            continue;
        }
        to[key] = isArray(from[key]) ? [] : {};
        deepCopy(to[key], from[key]);
    }
}
