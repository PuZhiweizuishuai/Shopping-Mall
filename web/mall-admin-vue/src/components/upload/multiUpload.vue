<template>
  <div>
    <el-upload
      :action="upload_Url"
      :data="dataObj"
      list-type="picture-card"
      :file-list="fileList"
      :on-remove="handleRemove"
      :on-success="handleUploadSuccess"
      :limit="maxCount"
      :http-request="uploadImg"
      :on-exceed="handleExceed"
    >
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt />
    </el-dialog>
  </div>
</template>
<script>
import { policy } from './policy'
import { getUUID } from '@/utils'
export default {
  name: 'multiUpload',
  props: {
    // 图片属性数组
    value: Array,
    // 最大上传图片数量
    maxCount: {
      type: Number,
      default: 30
    }
  },
  data () {
    return {
      dataObj: {},
      upload_Url: '',
      dialogVisible: false,
      dialogImageUrl: null,
      fileUrl: ''
    }
  },
  computed: {
    fileList () {
      let fileList = []
      for (let i = 0; i < this.value.length; i++) {
        fileList.push({ url: this.value[i] })
      }

      return fileList
    }
  },
  mounted () {},
  methods: {
    uploadImg (parameter) {
      var file = parameter.file
      var url = this.upload_url
      this.retrieveNewURL(file, (file, url) => {
                // 上传文件到服务器
        fetch(url, {
          method: 'PUT',
          body: file
        }).then(() => {
          // 处理文件URL
          url = url.substring(0, url.indexOf('?'))
          this.fileUrl = url
          // 文件上传成功
          parameter.onSuccess(url, file)
        }).catch((e) => {
          // 文件上传失败
          parameter.onError()
        })
      })
    },
    // 获取文件上传地址
    retrieveNewURL (file, cb) {
      fetch(this.$http.adornUrl(`/thirdparty/oss/policy?filename=${file.name}`), {
        method: 'GET' // or 'PUT'
      }).then(res => res.json())
            .catch(error => console.error('Error:', error))
            .then((response) => {
              this.upload_url = response.data.url
              cb(file, response.data.url)
            })
    },
    emitInput (fileList) {
      let value = []
      for (let i = 0; i < fileList.length; i++) {
        value.push(fileList[i].url)
      }
      this.$emit('input', value)
    },
    handleRemove (file, fileList) {
      this.emitInput(fileList)
    },
    handlePreview (file) {
      this.dialogVisible = true
      this.dialogImageUrl = file.url
    },
    beforeUpload (file) {

    },
    handleUploadSuccess (res, file) {
      this.fileList.push({
        name: file.name,
        url: res
      })
      this.emitInput(this.fileList)
    },
    handleExceed (files, fileList) {
      this.$message({
        message: '最多只能上传' + this.maxCount + '张图片',
        type: 'warning',
        duration: 1000
      })
    }
  }
}
</script>
<style>
</style>


