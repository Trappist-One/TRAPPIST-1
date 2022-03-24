/**
 * @program: T-1
 *
 * @description: el-radio-group控件[插槽]
 *
 * @author Bruce Lee ( copy )
 *
 * @create: 2021-03-10
 **/

export default {
  options(h, conf, key) {
    const list = []
    conf.slot.options.forEach(item => {
      if (conf.config.optionType === 'button') {
        list.push(<el-radio-button label={item.value}>{item.label}</el-radio-button>)
      } else {
        list.push(<el-radio label={item.value} border={conf.config.border}>{item.label}</el-radio>)
      }
    })
    return list
  }
}
