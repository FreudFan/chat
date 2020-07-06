<template>
    <div class="content">
        <div class="header">
            <div class="backButton" @click="$router.back()">< Back</div>
            <div class="title">消息记录</div>
        </div>
        <div class="logBox">
            <div class="log" v-for="log in logList">
                <span style="float: left;font-weight: bold">
                    {{log.senderUserId===$store.state.currentUser.id?$store.state.currentUser.name:$store.state.queryResult.name}} : </span>
                <span style="float: left">{{log.message}}</span>
                <span style="float: right">{{log.createTime}}</span>
                <br>
            </div>
            <div class="buttonBox">
                <span @click="prev"><<<&nbsp;&nbsp;&nbsp;&nbsp;</span>
                {{page}}
                <span @click="page++; getLog()">>>></span>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: "FriendMsgLog",
        data() {
            return {
                page: 1,
                logList: []
            }
        },
        mounted() {
            this.axios.get("/message/chat/" + this.$store.state.queryResult.id + '/' + this.page + '/20').then(response => {
                this.logList = response.data.list
            }).catch((error) => {
                console.log(error.response.status)
            });
        },
        methods: {
            getLog() {
                this.axios.get("/message/chat/" + this.$store.state.queryResult.id + '/' + this.page + '/20').then(response => {
                    this.logList = response.data.list
                }).catch((error) => {
                    console.log(error.response.status)
                });
            },
            prev() {
                if (this.page > 1) {
                    this.page--;
                    this.getLog()
                } else {
                    alert('已经是第一页')
                }
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
    }

    .title {
        height: 100%;
        font-size: 24px;
        font-weight: bolder;
        margin: 0 auto;
        text-align: center;
        line-height: 70px;
    }

    .backButton {
        position: absolute;
        cursor: pointer;
        font-size: 20px;
        color: #888888;
    }

    .log {
        height: 40px;
        line-height: 40px;
        border-bottom: 1px solid #CCCCCC;
        word-wrap: normal;
        word-break: break-all;
    }

    .buttonBox {
        margin-top: 20px;
        text-align: center;
        font-size: 20px;
    }

    .buttonBox > span {
        cursor: pointer;
        user-select: none;
    }
</style>