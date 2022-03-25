/**
 * @program: TRAPPIST-1
 *
 * @description: el-select控件[插槽]
 *
 * @author Bruce Lee ( copy )
 *
 * @create: 2021-03-10
 **/

export default {
  options(h, conf, key) {
    const list = []
    conf.slot.options.forEach(item => {
      list.push(<el-option label={item.label} value={item.value} disabled={item.disabled}></el-option>)
    })
    return list
  }
}
