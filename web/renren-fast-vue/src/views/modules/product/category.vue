<template>
  <div>

    <el-container>
      <el-header>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-switch v-model="draggableEnable"
                       active-text="开启拖拽排序"
                       inactive-text="关闭拖拽排序">
            </el-switch>
          </el-col>
          <el-col :span="6">
            <el-button v-if="draggableEnable"
                       type="success"
                       @click="batchSave">保存修改</el-button>
          </el-col>
          <el-col :span="6">
            <el-button type="danger"
                       @click="batchDelete">批量删除</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-tree :data="menus"
                 :props="defaultProps"
                 :expand-on-click-node="false"
                 show-checkbox
                 node-key="catId"
                 :default-expanded-keys="expandedKey"
                 :draggable="draggableEnable"
                 :allow-drop="allowDrop"
                 @node-drop="handleDrop"
                 ref="categoryMenuTree">
          <span class="custom-tree-node"
                slot-scope="{ node, data }">
            <span>{{ node.label }}</span>

            <span>
              <el-button type="text"
                         size="mini"
                         @click="() => editMenu(data)">
                修改节点
              </el-button>
              <el-button v-if="node.level <= 2"
                         type="text"
                         size="mini"
                         @click="() => append(data)">
                添加子节点
              </el-button>
              <el-button v-if="node.level == 3"
                         type="text"
                         size="mini"
                         @click="() => remove(node, data)">
                删除节点
              </el-button>
            </span>

          </span>
        </el-tree>

        <el-dialog title="添加新分类"
                   :visible.sync="dialogVisible"
                   :close-on-click-modal=false>
          <el-form :model="category">
            <el-form-item label="分类名称">
              <el-input v-model="category.name"
                        autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="图标">
              <el-input v-model="category.icon"
                        autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="单位">
              <el-input v-model="category.productUnit"
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

        <el-dialog title="修改分类"
                   :visible.sync="dialogEditVisible"
                   :close-on-click-modal=false>
          <el-form :model="category">
            <el-form-item label="分类名称">
              <el-input v-model="category.name"
                        autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="图标">
              <el-input v-model="category.icon"
                        autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="单位">
              <el-input v-model="category.productUnit"
                        autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer"
               class="dialog-footer">
            <el-button @click="dialogEditVisible = false">取 消</el-button>
            <el-button type="primary"
                       @click="sendEditMenu">确 定</el-button>
          </div>
        </el-dialog>
      </el-main>
    </el-container>

  </div>
</template>

<script>
export default {
  data () {
    return {
      category: {
        name: '',
        parentCid: -1,
        catLevel: -1,
        showStatus: 1,
        sort: 0,
        catId: null,
        icon: '',
        productUnit: ''
      },
      draggableEnable: false,
      updateNode: [],
      maxlevel: 0,
      addCategoryDataFather: {},
      menus: [],
      expandedKey: [],
      dialogVisible: false,
      dialogEditVisible: false,
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  methods: {
    getMenu () {
      this.$http({
        url: this.$http.adornUrl('/product/category/list/tree'),
        method: 'GET'
      }).then(({ data }) => {
        this.menus = data.data
      })
    },
    append (data) {
      this.dialogVisible = true
      // 父菜单ID
      this.category.parentCid = data.catId
      // 菜单层级
      this.category.catLevel = data.catLevel * 1 + 1
      this.addCategoryDataFather = data
      this.category.name = ''
      this.category.icon = ''
      this.category.productUnit = ''
    },
    remove (node, data) {
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
    addCategory () {
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
          // 数据清零
          this.initCategory()
        }
      }).catch(({ data }) => {
        this.$message({
          type: 'error',
          message: data.msg
        })
      })
    },
    editMenu (data) {
      // 发送请求获取最新的数据
      this.$http({
        url: this.$http.adornUrl('/product/category/info/' + data.catId),
        method: 'GET'
      }).then(({ data }) => {
        if (data.data != null) {
          this.category.name = data.data.name
          this.category.catId = data.data.catId
          this.category.catLevel = data.data.catLevel
          this.category.parentCid = data.data.parentCid
          this.category.sort = data.data.sort
          this.category.icon = data.data.icon
          this.category.productUnit = data.data.productUnit
        } else {
          this.$message({
            type: 'error',
            message: '当前节点可能已经被删除'
          })
        }
      })
      this.dialogEditVisible = true
    },
    sendEditMenu () {
      const { catId, name, icon, productUnit } = this.category
      this.$http({
        url: this.$http.adornUrl('/product/category/update'),
        method: 'PUT',
        data: this.$http.adornData({ catId: catId, name: name, icon: icon, productUnit: productUnit }, false)
      }).then(({ data }) => {
        this.dialogEditVisible = false
        this.$message({
          type: 'success',
          message: '修改成功!'
        })
        // 向列表添加数据
        this.expandedKey = [this.category.parentCid]
        this.getMenu()
        // 数据清零
        this.initCategory()
      })
    },
    initCategory () {
      this.category.name = ''
      this.category.parentCid = -1
      this.category.catLevel = -1
      this.category.showStatus = 1
      this.category.sort = 0
      this.category.catId = null
      this.category.icon = ''
      this.category.productUnit = ''
    },
    allowDrop (draggingNode, dropNode, type) {
      this.maxlevel = 0
      this.countNodeLevel(draggingNode.data)
      const deep = Math.abs(this.maxlevel - draggingNode.level) + 1
      if (type === 'inner') {
        return (deep + dropNode.level) <= 3
      } else {
        return (deep + dropNode.parent.level) <= 3
      }
    },
    // 统计当前节点被拖动的总层数
    countNodeLevel (node) {
      if (node.childNodes != null && node.childNodes.length > 0) {
        for (let i = 0; i < node.childNodes.length; i++) {
          if (node.childNodes[i].level > this.maxlevel) {
            this.maxlevel = node.childNodes[i].level
          }
          this.countNodeLevel(node.childNodes[i])
        }
      }
    },
    // 拖拽成功
    handleDrop (draggingNode, dropNode, dropType, ev) {
      // 当前节点最新的父节点ID
      let pCid = 0
      let siblings = null
      if (dropType === 'before' || dropType === 'after') {
        pCid = dropNode.parent.data.catId === undefined ? 0 : dropNode.parent.data.catId
        siblings = dropNode.parent.childNodes
      } else {
        pCid = dropNode.data.catId
        siblings = dropNode.childNodes
      }
      // 当前拖拽节点的最新顺序
      for (let i = 0; i < siblings.length; i++) {
        if (siblings[i].data.catId === draggingNode.data.catId) {
          let catLevel = draggingNode.level
          if (siblings[i].level !== draggingNode.level) {
            // 当前节点的层级发生变化
            catLevel = siblings[i].level
            // 修改子节点的层级
            this.updateChildNodeLevel(siblings[i])
          }
          this.updateNode.push({ catId: siblings[i].data.catId, sort: i, parentCid: pCid, catLevel: catLevel })
        } else {
          this.updateNode.push({ catId: siblings[i].data.catId, sort: i })
        }
      }
    },
    updateChildNodeLevel (node) {
      if (node.childNodes.length > 0) {
        for (let i = 0; i < node.childNodes.length; i++) {
          const cNode = node.childNodes[i].data
          this.updateNode.push({ catId: cNode.catId, catLevel: node.childNodes[i].level })
          this.updateChildNodeLevel(node.childNodes[i])
        }
      }
    },
    batchSave () {
      if (this.updateNode === null || this.updateNode.length === 0) {
        this.$message({
          type: 'info',
          message: '没有进行任何修改'
        })
        return
      }
      // 发送修改数据
      this.$http({
        url: this.$http.adornUrl('/product/category/update/sort'),
        method: 'PUT',
        data: this.$http.adornData(this.updateNode, false)
      }).then(({ data }) => {
        this.$message({
          type: 'success',
          message: '修改成功!'
        })
        this.updateNode = []
        this.maxlevel = 0
        this.getMenu()
      })
    },
    batchDelete () {
      const checkNodes = this.$refs.categoryMenuTree.getCheckedNodes()
      const catId = []
      let name = ''
      if (checkNodes.length === 0) {
        this.$message({
          type: 'info',
          message: '没有选中任何对象'
        })
        return
      }
      for (let i = 0; i < checkNodes.length; i++) {
        catId.push(checkNodes[i].catId)
        name = name + checkNodes[i].name + ', '
      }
      this.$confirm(`此操作将永久删除【 ${name} 】分类, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/product/category/delete'),
          method: 'DELETE',
          data: this.$http.adornData(catId, false)
        }).then(({ data }) => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.getMenu()
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
    }
  },
  created () {
    this.getMenu()
  }
}
</script>

<style>
</style>