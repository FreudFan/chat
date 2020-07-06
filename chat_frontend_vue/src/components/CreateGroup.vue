<template>
    <div class="content">
        <div class="header">
            <div class="headerBar"></div>
            <div class="backButton" @click="$router.push('/main/groupchat')">< Back</div>
            <div class="title">
                <span>创建群</span>
            </div>
            <div class="createWindow">
                <div class="infoRow">群名称：<input type="text" v-model="groupInfo.name"></div>
                <div class="infoRow">群介绍：<input type="text" v-model="groupInfo.info"></div>
                <div class="infoRow submitButton" v-text="submitButton" @click="createGroup"></div>
            </div>
        </div>
    </div>
</template>

<script>
    import router from "../router";

    export default {
        router,
        data() {
            return {
                groupInfo: {
                    name: null,
                    info: null
                },
                submitButton: "提交"
            }
        },
        methods: {
            createGroup() {
                this.axios.post('/group/create', this.groupInfo).then(() => {
                    this.submitButton = "创建成功！";
                    // 获取群组
                    this.axios.get("/user/group").then(response => {
                        this.$store.commit('setGroupList', response.data);
                    }).catch((error) => {
                        if (error.response.status === 401) {
                            router.replace("/welcome")
                        }
                    })
                    setTimeout(function () {
                        router.push('/main');
                    }, 1000)
                }).catch(error => {
                    alert('参数错误！');
                    console.log(error.response.data)
                });
            }
        }
    }
</script>

<style scoped>
    .content {
        padding: 25px;
    }

    .header {
        width: 100%;
        height: 60px;
        border-bottom: 1px solid #CCCCCC;
        text-align: right;
    }

    .title {
        height: 100%;
        font-size: 24px;
        font-weight: bolder;
        text-align: center;
        line-height: 70px;
    }

    .backButton {
        position: absolute;
        cursor: pointer;
        font-size: 20px;
        color: #888888;
    }

    .infoRow {
        text-align: center;
        height: 50px;
        margin: 0 200px;
        font-size: 16px;
        line-height: 70px;
        border-bottom: 1px solid #CCCCCC;
    }

    .submitButton {
        font-weight: bold;
        cursor: pointer;
    }
</style>