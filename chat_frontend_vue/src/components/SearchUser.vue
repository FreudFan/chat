<template>
    <div class="content">
        <div class="header">
            <div class="backButton" @click="$router.push('/main')">< Back</div>
            <div class="title">搜索用户</div>
        </div>
        <div class="result" v-for="item in $store.state.searchResult">
            <div class="resultRow">
                <div class="userName">{{item.name}}</div>
                <div class="isFriend">
                    <span v-show="item.isFriend">好友</span>
                    <div class="addButton" v-show="!item.isFriend" @click="addFriend(item.id)">加为好友</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "searchResult",
        methods: {
            addFriend(id) {
                this.axios.post("/friend/request/" + id).then(response => {
                    alert('好友申请发送成功！')
                }).catch(function (error) {
                    alert(error.response.data);
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

    .result {
        height: 50px;
        margin: 0 200px;
        font-size: 16px;
        line-height: 70px;
        border-bottom: 1px solid #CCCCCC;
    }

    .resultRow {
        display: flex;
    }

    .userName {
        width: 70%;
    }

    .isFriend {
        width: 30%;
        text-align: center;
    }

    .addButton {
        cursor: pointer;
    }
</style>