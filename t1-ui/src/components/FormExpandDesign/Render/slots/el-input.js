/**
 * @program: TRAPPIST-1
 *
 * @description: el-input控件[插槽]
 *
 * @author Bruce Lee ( copy )
 *
 * @create: 2021-03-10
 **/

export default {
  prepend(h, conf, key) {
    return `<template slot="prepend">{conf.slot[key]}</template>`
  },
  append(h, conf, key) {
    return `<template slot="append">{conf.slot[key]}</template>`
  }
}
