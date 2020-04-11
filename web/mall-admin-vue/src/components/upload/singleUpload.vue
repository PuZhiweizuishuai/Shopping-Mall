<template> 
  <div>
    <el-upload :action="upload_url"
               list-type="picture"
               :http-request="uploadImg"
               :limit="1"
               :on-success="uploadImgSuccess"
               :on-remove="handleRemove"
               accept="jpg">
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>
  </div>
</template>
<script>
export default {
  props: {
    // 此处一定要用value
    value: {
      type: String
    }
  },
  data: function () {
    return {
      upload_url: '', // 上传URL
      upload_name: '', // 图片或视频名称
      ad_url: '', // 上传后的图片或视频URL
      ad_url_list: [], // 预览列表
      fileUrl: this.value
    }
  },
  methods: {

    uploadImg (parameter) {
      // this.axios.get('./getToken').then((response) => { // 获取token
      //   const param = new FormData() // 创建form对象
      //   param.append('file', f.file)// 通过append向form对象添加数据
      //   param.append('token', response.data.token)// 通过append向form对象添加数据
      //   param.append('key', response.data.key)// 添加form表单中其他数据
      //   const config = {
      //     headers: { 'Content-Type': 'multipart/form-data' }
      //   }  // 添加请求头
      //   this.axios.post(f.action, param, config)// 上传图片
      //     .then(response => {
      //       f.onSuccess(response.data)
      //     })
      //     .catch(({ err }) => {
      //       f.onError()
      //     })
      // })
      //   .catch(() => {
      //     f.onError()
      //   })
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
          parameter.onSuccess()

          this.$emit('input', url)
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
    uploadImgSuccess (response, file, fileList) {
      // 缓存接口调用所需的文件路径
      console.log('uploadImgSuccess')
    },
    handleRemove (file, fileList) {
      // 更新缓存文件
      this.$emit('input', '')
      console.log('文件删除')
    }
  },
  created () {
    console.log(this.fileUrl)
  }
}
</script>
<style>
</style>


