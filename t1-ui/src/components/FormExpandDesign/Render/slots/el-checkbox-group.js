/**
 * @program: T-1
 *
 * @description: el-checkbox-group控件[插槽]
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
        list.push("<el-checkbox-button label={item.value}>{item.label}</el-checkbox-button>")
      } else {
        list.push("<el-checkbox label={item.value} border={conf.config.border}>{item.label}</el-checkbox>")
      }
    })
    return list
  }
}
