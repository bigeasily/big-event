<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'

import { ref } from 'vue'
//文章列表查询
import { articleCategoryListService } from '@/api/article.js'
//文章列表查询
import { articleListService } from '@/api/article.js'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { articleAddService } from '@/api/article.js'
import { ElMessage } from 'element-plus'
import { ElMessageBox } from 'element-plus'
//文章分类数据模型
const categorys = ref([])
//弹窗标题
const title = ref('')
//用户搜索时选中的分类id
const categoryId = ref('')

//用户搜索时选中的发布状态
const state = ref('')

//文章列表数据模型
const articles = ref([])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  if (typeof size === 'number' && !isNaN(size)) {
    pageSize.value = size;
    getArticles();
  } else {
    console.error('Invalid pageSize:', size);
    pageSize.value = 3; // 回退到默认值
  }
};
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    getArticles()
}

const getArticleCategoryList = async () => {
    //获取所有分类
    let resultC = await articleCategoryListService();
    categorys.value = resultC.data
}
getArticleCategoryList();
const getArticles = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        categoryId: categoryId.value ? categoryId.value : null,
        state: state.value ? state.value : null
    }
    let result = await articleListService(params);
    //渲染列表数据
    articles.value = result.data.items
    //为列表中添加categoryName属性
    for (let i = 0; i < articles.value.length; i++) {
        let article = articles.value[i];
        for (let j = 0; j < categorys.value.length; j++) {
            if (article.categoryId == categorys.value[j].id) {
                article.categoryName = categorys.value[j].categoryName
            }
        }
    }
    //渲染总条数
    total.value = result.data.total
}
getArticles()
import { Plus } from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)
//添加表单数据模型
const articleModel = ref({
    title: '',
    categoryId: '',
    coverImg: '',
    content: '',
    state: ''
})
//修改分类回显
const updateArticleEcho = (row) => {
    title.value = '修改文章'
    visibleDrawer.value = true
    //将row中的数据赋值给categoryModel
    articleModel.value.title = row.title
    articleModel.value.content = row.content
    // articleModel.value.state = row.state
    articleModel.value.coverImg = row.coverImg
    articleModel.value.categoryId = row.categoryId
    //修改的时候必须传递分类的id，所以扩展一个id属性
    articleModel.value.id = row.id
}
//添加文章
const addArticle = async (state) => {
    getArticleCategoryList()
    title.value = '添加文章'
    articleModel.value.state = state
    let result = await articleAddService(articleModel.value);
    ElMessage.success('添加成功')
    //再次调用getArticles,获取文章
    getArticles()
    //隐藏抽屉
    visibleDrawer.value = false
}
import { articleUpdateService } from '@/api/article.js'
//修改文章
const updateArticle = async () => {
    getArticleCategoryList()

    let result = await articleUpdateService(articleModel.value)
    ElMessage.success('修改成功')
    //隐藏弹窗
    visibleDrawer.value = false
    //再次访问后台接口，查询所有分类
    getArticles()
}


import { articleDeletService } from '@/api/article.js'
//删除文章
const deleteArticle = (row) => {
    ElMessageBox.confirm(
        '你确认删除该分类信息吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'error',
            center: true,
        }
    )
        .then(async () => {
            //用户点击了确认
            let result = await articleDeletService(row.id)
            ElMessage.success('删除成功')
            getArticles()
        })
        .catch(() => {
            //用户点击了取消

        })
}

const content = ref('');
const editorKey = ref(0);
const onEditorReady = () => {
};
// 当需要重新初始化编辑器时
const resetEditor = () => {
    editorKey.value += 1; // 改变 key 强制重新渲染
    content.value = ''; // 清空内容
};

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>文章管理</span>
                <div class="extra">
                    <el-button type="primary" :title="title"
                        @click="visibleDrawer = 'true'; title = '添加文章'">添加文章</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="文章分类：">
                <el-select placeholder="请选择" v-model="categoryId"
                    style="width: 200px; min-height: 40px; line-height: normal;">
                    <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="发布状态：">
                <el-select placeholder="请选择" v-model="state"
                    style="width: 200px; min-height: 40px; line-height: normal;">
                    <el-option label="已发布" value="已发布"></el-option>
                    <el-option label="草稿" value="草稿"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getArticles">搜索</el-button>
                <el-button @click="categoryId = ''; state = ''">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 文章列表 -->
        <el-table :data="articles" style="width: 100%">
            <el-table-column label="文章标题" width="400" prop="title"></el-table-column>
            <el-table-column label="分类" prop="categoryName"></el-table-column>
            <el-table-column label="发表时间" prop="createTime"> </el-table-column>
            <el-table-column label="状态" prop="state"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="updateArticleEcho(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteArticle(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" :title="title" direction="rtl" size="50%">
            <!-- 添加文章表单 -->
            <el-form :model="articleModel" label-width="100px">
                <el-form-item label="文章标题">
                    <el-input v-model="articleModel.title" placeholder="请输入标题"></el-input>
                </el-form-item>
                <el-form-item label="文章分类">
                    <el-select placeholder="请选择" v-model="articleModel.categoryId">
                        <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="文章封面">

                    <el-upload class="avatar-uploader" :auto-upload="false" :show-file-list="false">
                        <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="文章内容">
                    <div class="editor">
                        <quill-editor :key="editorKey" theme="snow" v-model:content="articleModel.content"
                            contentType="html" @ready="onEditorReady">
                        </quill-editor>
                    </div>
                </el-form-item>
                <!-- 调试：显示当前 title 值
                    <p>当前title值: "{{ title }}"</p> -->
                <el-form-item v-if="title == '添加文章'">
                    <el-button type="primary" @click="addArticle('已发布')">发布</el-button>
                    <el-button type="info" @click="addArticle('草稿')">草稿</el-button>
                </el-form-item>
                <el-form-item v-if="title == '修改文章'">
                    <el-button type="primary" @click="updateArticle()">确定</el-button>
                    <el-button type="info" @click="visibleDrawer = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
    </el-card>
</template>
<style lang="scss" scoped>
/* 抽屉样式 */
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}

.editor {
    width: 100%;

    :deep(.ql-editor) {
        min-height: 200px;
    }
}

.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}
</style>