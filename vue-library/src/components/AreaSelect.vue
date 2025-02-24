<template>

    <el-cascader ref="cascader" v-model="ruleForm.area" :options="options" clearable :placeholder="placeholder"
                 @change="handleChangeArea" style="width:100%"></el-cascader>

</template>
<script>
import provAndCity from '@/data/area.json'//引入地区文件
export default {
  props: {
    placeholder: {
      type: String,
      default: '请选择地址'
    },
    myArea: {
      type: [Array, Object, String],
      // default: []
    }
  },
  model: {
    //需要定义哪一个props可以用v-model绑定属性
    prop: 'myArea',
    //声明一个事件，调用之后，就会改变父级容器的值
    event: 'handlerValueChange'
  },
  data() {
    return {
      options: [],
      ruleForm: {},
      title: '',
      chooseAddressList: [],
      props: {},
    }
  },
  watch: {
    myArea: {
      immediate: true, // 立即执行 :当刷新页面时会立即执行一次handler函数
      handler(val) {
        this.ruleForm.area = val
        // console.log('触发监听');
      }
    }
  },
  mounted() {
    this.options = provAndCity
    // this.init()
  },
  methods: {
    // 选择省市区地址
    handleChangeArea(e) {
      if (e && e.length > 0) {
        this.ruleForm.province = this.$refs["cascader"].getCheckedNodes()[0].pathLabels[0]
        this.ruleForm.city = this.$refs["cascader"].getCheckedNodes()[0].pathLabels[1]
        this.ruleForm.label = this.$refs["cascader"].getCheckedNodes()[0].pathLabels
      } else {
        this.ruleForm.province = ''
        this.ruleForm.city = ''
      }
      console.log(this.ruleForm, '选择的地址信息地址选择组件内');
      this.$emit('handlerValueChange', this.ruleForm.area)
      this.$emit('change', this.ruleForm)
    },
  },
}
</script>
<style>
</style>