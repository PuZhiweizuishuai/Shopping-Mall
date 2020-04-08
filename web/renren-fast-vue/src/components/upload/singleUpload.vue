<template> 
  <div>
    <el-upload class="upload-demo"
               ref="upload"
               :action="url"
               :on-preview="handlePreview"
               :on-remove="handleRemove"
               :multiple="false"
               :limit=1
               :http-request="uploadFile"
               :file-list="fileList"
               :auto-upload="false">
      <el-button slot="trigger"
                 size="small"
                 type="primary">选取文件</el-button>
      <el-button style="margin-left: 10px;"
                 size="small"
                 type="success"
                 @click="submit">上传到服务器</el-button>
      <el-button type="success" @click="submitUpload">get url</el-button>
      <div slot="tip"
           class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>
  </div>
</template>
<script>
export default {
  data() {
    return {
      url: '',
      fileList: [],
      file: []
    }
  },
  methods: {
    uploadFile(param) {
      console.log('http')
      const formData = new FormData()
      formData.append('file', param.file)
      this.$http({
        url: this.url,
        method: 'PUT',
        data: param.file
      }).then(({ data }) => {
      })
    },
    submit() {
      this.$refs.upload.submit()
    },
    submitUpload() {
      // this.$refs.upload.submit();
      console.log('上传')
      this.$http({
        url: this.$http.adornUrl('/thirdparty/oss/policy'),
        method: 'GET'
      }).then(({ data }) => {
        this.url = data.data.url
        console.log(this.url)
      })
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
    }
  }
}
</script>
<style>
</style>


