/**
 * 获取当前时间 yyyy-MM-dd
 *
 * @returns {string}
 */
export function getNowFormatDate () {
  let date = new Date()
  let split = '-'
  let year = date.getFullYear()
  let month = date.getMonth() + 1
  let day = date.getDate()
  if (month >= 1 && month <= 9) {
    month = '0' + month
  }
  if (day >= 0 && day <= 9) {
    day = '0' + day
  }
  return year + split + month + split + day
}
