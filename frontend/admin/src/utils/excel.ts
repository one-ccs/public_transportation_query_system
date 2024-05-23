import * as XLSX from 'xlsx';

/**
 * 导入 Excel
 * @param file 文件对象
 */
export function importExcel(file: File) {
    return new Promise(function (resolve, reject) {
        const reader = new FileReader();

        reader.onload =  (e: any) => {
            const data = e.target.result;

            let dataJson = XLSX.read(data, {
                type: 'binary',
            });

            const sheetName = dataJson.SheetNames[0];
            const result = XLSX.utils.sheet_to_json(dataJson.Sheets[sheetName]);
            resolve(result);
        };
        reader.readAsArrayBuffer(file);
    });
}

/**
 * 导出 Excel
 * @param name 保存名称
 * @param list 数据列表
 */
export function exportExcel(name: string, list: any[]) {
    let WorkSheet = XLSX.utils.aoa_to_sheet(list);
    let new_workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(new_workbook, WorkSheet, '第一页');
    XLSX.writeFile(new_workbook, `${name}.xlsx`);
}
