<template>
  <div>
    <el-tree :data="menus"
             :props="defaultProps"
             :expand-on-click-node="false"
             show-checkbox
             node-key="catId"
             :default-expanded-keys="expandedKey">
      <span class="custom-tree-node"
            slot-scope="{ node, data }">
        <span>{{ node.label }}</span>

        <span>
          <el-button v-if="node.level <= 2"
                     type="text"
                     size="mini"
                     @click="() => append(data)">
            添加子节点
          </el-button>
          <el-button v-if="data.children.length == 0"
                     type="text"
                     size="mini"
                     @click="() => remove(node, data)">
            删除节点
          </el-button>
        </span>

      </span>
    </el-tree>

    <el-dialog title="添加新的分类"
               :visible.sync="dialogVisible">
      <el-form :model="category">
        <el-form-item label="分类名称">
          <el-input v-model="category.name"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分类优先级（数字越大显示优先级越高）">
          <el-input v-model="category.sort"
                    autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer"
           class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="addCategory">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      category: {
        name: '',
        parentCid: -1,
        catLevel: -1,
        showStatus: 1,
        sort: 0
      },
      addCategoryDataFather: {},
      menus: [],
      expandedKey: [],
      dialogVisible: false,
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  methods: {
    getMenu() {
      this.$http({
        url: this.$http.adornUrl('/product/category/list/tree'),
        method: 'GET'
      }).then(({ data }) => {
        this.menus = data.data
      })
    },
    append(data) {
      this.dialogVisible = true
      // 父菜单ID
      this.category.parentCid = data.catId
      // 菜单层级
      this.category.catLevel = data.catLevel * 1 + 1
      this.addCategoryDataFather = data
    },

    remove(node, data) {
      const parent = node.parent
      const children = parent.data.children || parent.data
      const index = children.findIndex(d => d.id === data.id)

      this.$confirm(`此操作将永久删除 【${data.name}】 分类, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var ids = [data.catId]
        this.$http({
          url: this.$http.adornUrl('/product/category/delete'),
          method: 'DELETE',
          data: this.$http.adornData(ids, false)
        }).then(({ data }) => {
          children.splice(index, 1)
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(({ data }) => {
          this.$message({
            type: 'error',
            message: data.msg
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    addCategory() {
      if (this.category.name === '') {
        this.$message({
          type: 'info',
          message: '请先输入分类名'
        })
        return
      }
      this.$http({
        url: this.$http.adornUrl('/product/category/save'),
        method: 'POST',
        data: this.$http.adornData(this.category, false)
      }).then(({ data }) => {
        if (data.code !== 0) {
          this.$message({
            type: 'error',
            message: data.msg
          })
        } else {
          this.dialogVisible = false
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          // 向列表添加数据
          this.expandedKey = [this.addCategoryDataFather.catLevel]
          this.getMenu()
        }
      }).catch(({ data }) => {
        this.$message({
          type: 'error',
          message: data.msg
        })
      })
    }
  },
  created() {
    this.getMenu()
  }
}
</script>

<style>
</style>