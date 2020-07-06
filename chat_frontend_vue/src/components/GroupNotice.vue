<template>
    <div class="content">
        <div class="header">
            <div class="backButton" @click="$router.push('/main/groupchat')">< Back</div>
            <div class="title">
                <span>群公告</span>
            </div>
            <div class="noticeList">
                <div class="notice" v-for="notice in reverseNotice">
                    <span style="float: left"><strong>{{notice.title}} : </strong>{{notice.content}}</span>
                    <span style="float: right;margin-left: 5px;color: red;cursor: pointer"
                          @click="deleteNotice(notice.id)" v-if="currentRoleId > 0"> 删除</span>
                    <span style="float: right">{{notice.updateTime}}</span>
                </div>
            </div>

            <div class="sendNotice" v-if="currentRoleId > 0">
                <div class="title">
                    <span>发布群公告</span>
                </div>
                <input class="noticeTitle" type="text" placeholder="群公告标题" v-model="sendNotice.title">
                <textarea class="noticeInput" v-model="sendNotice.content"></textarea>
                <div class="infoRow" @click="submitNotice" style="cursor: pointer"><strong>提交</strong></div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "GroupNotice",
        data() {
            return {
                currentRoleId: 0,
                noticeList: [],
                sendNotice: {}
            }
        },
        computed: {
            reverseNotice() {
                return this.noticeList.reverse();
            }
        },
        mounted() {
            // 检查群权限
            this.axios.get("/group/queryUserRole/" + this.$store.state.queryResult.id).then(response => {
                this.currentRoleId = response.data;
            }).catch(function (error) {
                console.log(error.response.data);
            });
            // 拉取群公告
            this.axios.get("/group/showNoticeList/" + this.$store.state.queryResult.id).then(response => {
                console.log(response.data)
                this.noticeList = response.data;
            }).catch(function (error) {
                console.log(error.response.data);
            });
        },
        methods: {
            submitNotice() {
                let req = {
                    groupId: this.$store.state.queryResult.id,
                    title: this.sendNotice.title,
                    content: this.sendNotice.content
                }
                this.axios.post("/group/addNotice", req).then(() => {
                    alert('群公告发布成功');
                    this.$router.go(0);
                }).catch(function (error) {
                    console.log(error.response.data);
                });
            },
            deleteNotice(e) {
                this.axios.post("/group/deleteNotice/" + this.$store.state.queryResult.id + '/' + e).then(() => {
                    alert('群公告删除成功');
                    this.$router.go(0);
                }).catch(function (error) {
                    console.log(error.response.data);
                });
            }
        }
    }
</script>

<style scoped>
    .content {
        padding: 25px;
        height: 100%;
        box-sizing: border-box;
        overflow: hidden;
    }

    .header {
        position: absolute;
        width: 650px;
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

    .notice {
        height: 50px;
        line-height: 50px;
        text-align: center;
        border-bottom: 1px solid #CCCCCC;
    }

    .sendNotice {
        text-align: center;
    }

    .noticeTitle {
        width: 40%;
        margin-bottom: 20px;
    }

    .noticeInput {
        resize: none;
        width: 80%;
        height: 100px;
    }

    input::-webkit-input-placeholder {
        text-align: center;
    }
</style>