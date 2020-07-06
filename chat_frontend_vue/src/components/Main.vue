<template>
    <div class="main">
        <div class="left">
            <div class="leftHeader">
                <div class="avatar">
                    <span v-text="$store.state.currentUser.name.substr(0,1)"></span>
                </div>
                <p v-text="$store.state.currentUser.name" class="userName" @click="$router.push('/main/editinfo')"></p>
            </div>
            <div class="search">
                <label>
                    <input type="text" v-model="search" placeholder="用户名称/群名称">
                </label>
                <div class="searchUser" @click="searchUser">搜人</div>
                <div class="searchGroup" @click="searchGroup">搜群</div>
            </div>
            <div class="tabBox">
                <div class="tab" v-for="tab in tabList" :class="{active : $store.state.currentTab === tab}"
                     @click="selected(tab)" v-text="tab">
                </div>
            </div>
            <div class="listBox">
                <div class="list" v-show="$store.state.currentTab==='好友'">
                    <div class="friendRequest" @click="$router.push('/main/friendrequest')"
                         :class="{red:msgUserId === 'request'}">好友请求
                    </div>
                    <div v-for="friendGroup in $store.state.friendList" class="friendGroupList">
                        <span class="friendGroupName">{{friendGroup.groupName}}</span>
                        <div v-for="user in friendGroup.users" class="friendsList"
                             @click="queryFriend(user.id); msgUserId = null" :class="{red:msgUserId === user.id}">
                            <div class="friendsAvatar">
                                <span v-text="user.name.substr(0,1)"></span>
                            </div>
                            <div v-text="user.name" class="friendsName"></div>
                        </div>
                    </div>
                </div>
                <div class="list" v-show="$store.state.currentTab==='群组'">
                    <div class="groupList createGroup" @click="$router.push('/main/createGroup')">+ 创建群聊</div>
                    <div v-for="group in $store.state.groupList" class="groupList" @click="queryGroup(group.id)">
                        <span class="groupName">{{group.name}}</span>
                    </div>
                </div>
            </div>
            <div class="logout" @click="logout">注销</div>
        </div>
        <div class="right">
            <router-view></router-view>
        </div>
    </div>
</template>
<script>
    import router from "../router";

    export default {
        router,
        data() {
            return {
                msgUserId: null,
                search: null,
                tabList: ["好友", "群组"],
                friend: null
            }
        },
        mounted() {
            // 获取好友
            this.axios.get("/user/friend").then(response => {
                this.$store.commit('setFriendList', response.data);
            }).catch((error) => {
                if (error.response.status === 401) {
                    router.replace("/welcome")
                }
            });
            // 获取群组
            this.axios.get("/user/group").then(response => {
                this.$store.commit('setGroupList', response.data);
            }).catch((error) => {
                if (error.response.status === 401) {
                    router.replace("/welcome")
                }
            });
            // 初始化websocket
            if (typeof (WebSocket) === "undefined") {
                alert("您的浏览器不支持WebSocket!")
            } else {
                this.socket = new WebSocket('ws://127.0.0.1:8088/ws')
                this.socket.onopen = this.open
                this.socket.onerror = this.error
                this.socket.onmessage = this.getMessage
            }
            this.scroll();
        },
        destroyed() {
            this.socket.onclose = this.close
        },
        methods: {
            // 选择Tab
            selected(item) {
                this.$store.commit('setCurrentTab', item)
            },
            // 注销
            logout() {
                this.axios.post("/auth/logout").then(() => {
                    sessionStorage.clear();
                    router.replace("/welcome");
                }).catch(function (error) {
                    console.log(error.response.data);
                });
            },
            // 查询好友信息
            queryFriend(id) {
                this.axios.get("/friend/info/" + id).then(response => {
                    response.data.recMsg = [];
                    this.$store.commit("setQueryResult", response.data);
                    this.$router.push('/main/friendchat')
                }).catch(function (error) {
                    console.log(error.response.data);
                });
            },
            // 查询群组信息
            queryGroup(id) {
                this.axios.get("/group/infoGroup/" + id).then(response => {
                    response.data.recMsg = [];
                    this.$store.commit("setQueryResult", response.data);
                    this.$router.push('/main/groupchat')
                }).catch(function (error) {
                    console.log(error.response.data);
                });
            },
            // 搜索用户
            searchUser() {
                this.axios.get("/user/queryUser?name=" + this.search).then(response => {
                    if (response.data.length !== 0) {
                        this.$store.commit("setSearchResult", response.data);
                        router.push("/main/searchuser")
                    } else alert("无搜索结果");
                }).catch(function (error) {
                    alert(error.response.data.error);
                    console.log(error.response.data);
                });
            },
            // 搜索群组
            searchGroup() {
                this.axios.get('/group/queryGroup/' + this.search).then(response => {
                    if (response.data.length !== 0) {
                        this.$store.commit("setSearchResult", response.data);
                        router.push("/main/searchgroup")
                    } else alert("无搜索结果");
                }).catch(function (error) {
                    alert(error.response.data.error);
                    console.log(error.response.data.error);
                });
            },
            // 定义websocket基础方法
            open() {
                console.log("Socket connected");
                this.init();
            },
            error() {
                console.log("Socket error")
            },
            send(request) {
                this.socket.send(JSON.stringify(request));
            },

            getMessage(response) {
                let data = JSON.parse(response.data);
                console.log(data);
                // 聊天消息
                if (data.action === 2) {
                    let date = new Date();
                    data.chatMsg.time = date.toLocaleString('chinese', {hour12: false});
                    this.$store.commit("pushRecMsg", data.chatMsg);
                    if (this.$store.state.queryResult.id !== data.id) {
                        console.log();
                    }
                    this.scroll();
                    this.msgUserId = data.chatMsg.senderId;
                }
                // 好友申请
                if (data.action === 9) {
                    this.msgUserId = 'request'
                }
                // 好友申请通过
                if (data.action === 5) {
                    this.axios.get("/user/friend").then(response => {
                        this.$store.commit('setFriendList', response.data);
                    }).catch((error) => {
                        if (error.response.status === 401) {
                            router.replace("/welcome")
                        }
                    });
                }
            },
            close() {
                console.log("Socket closed")
            },
            // 初始化
            init() {
                let initReq = {
                    "action": 1,
                    "chatMsg": {
                        "senderId": this.$store.state.currentUser.id
                    }
                };
                initReq.chatMsg.senderId = this.$store.state.currentUser.id;
                this.send(initReq)
            },
            // 当前路径包含"chat"时，定位滚动条在最下方
            scroll() {
                let href = window.location.href;
                if (href.indexOf("chat") > 0) {
                    this.$nextTick(() => {
                        let msg = document.getElementById('scroll');
                        msg.scrollTop = msg.scrollHeight
                    })
                }
            }
        }
    }
</script>

<style scoped>
    .main {
        width: 1000px;
        height: 100%;
        margin: 0 auto;
    }

    .left {
        float: left;
        background-color: rgb(46, 50, 56);
        width: 30%;
        height: 100%;
    }

    .right {
        float: right;
        background-color: rgb(238, 238, 238);
        width: 70%;
        height: 100%;
    }

    .leftHeader {
        height: 60px;
        border-bottom: 1px solid rgb(36, 39, 44);
        padding: 12px 30px;
    }

    .avatar {
        width: 60px;
        height: 60px;
        line-height: 60px;
        background-color: deepskyblue;
        border-radius: 50%;
        text-align: center;
        color: white;
        float: left;
    }

    .avatar > span {
        font-size: 30px;
    }

    .userName {
        width: 180px;
        height: 100%;
        float: right;
        margin: 0;
        font-size: 30px;
        line-height: 60px;
        color: white;
        text-align: center;
        cursor: pointer;
    }

    .search {
        height: 40px;
        padding: 10px;
    }

    .search > label > input {
        width: 60%;
        display: block;
        height: 40px;
        border-radius: 10px 0 0 10px;
        padding-left: 20px;
        border: none;
        outline: none;
        margin: 0 auto;
        background-color: rgb(238, 238, 238);
        float: left;
    }


    .searchUser, .searchGroup {
        color: white;
        text-align: center;
        line-height: 40px;
        width: 15%;
        height: 40px;
        background-color: #444444;
        float: left;
        cursor: pointer;
    }

    .searchUser:hover, .searchGroup:hover {
        background-color: #222222;
    }

    .searchGroup {
        border-radius: 0 10px 10px 0;
        border-left: 1px solid #222222;
    }

    .tabBox {
        height: 50px;
    }

    .tab {
        margin-top: 6px;
        width: 49%;
        float: left;
        text-align: center;
        color: white;
        background-color: #222222;
        height: 40px;
        line-height: 40px;
        cursor: pointer;
        border-right: 1px solid #2E3238;
    }

    .active {
        background-color: #444;
    }

    .listBox {
    }

    .friendRequest {
        height: 60px;
        line-height: 60px;
        padding-left: 20px;
        color: white;
        background: #222222;
        font-weight: bold;
        cursor: pointer;
    }

    .friendGroupList {
        background-color: rgb(46, 50, 56);
        border-top: 1px solid rgb(46, 50, 56);
        color: white;
    }

    .friendGroupName {
        display: inline-block;
        height: 60px;
        line-height: 60px;
        padding-left: 20px;
    }

    .friendsList {
        height: 60px;
        border-top: 1px solid rgb(46, 50, 56);
        padding-left: 40px;
        vertical-align: center;
        background-color: #222222;
        cursor: pointer;
    }

    .friendsAvatar {
        margin: 15px 0;
        width: 30px;
        height: 30px;
        line-height: 30px;
        background-color: deepskyblue;
        border-radius: 50%;
        text-align: center;
        color: white;
        float: left;
    }

    .friendsAvatar > span {
        font-size: 15px;
    }

    .friendsName {
        height: 60px;
        width: 100px;
        float: left;
        line-height: 60px;
        padding-left: 15px;
    }

    .groupList {
        background-color: #222222;
        border-top: 1px solid rgb(46, 50, 56);
        color: white;
        cursor: pointer;
    }

    .groupName {
        display: inline-block;
        height: 60px;
        line-height: 60px;
        padding-left: 20px;
    }

    .logout {
        position: absolute;
        bottom: 0;
        color: white;
        font-size: 20px;
        width: 300px;
        height: 60px;
        line-height: 60px;
        text-align: center;
        background-color: #222222;
        cursor: pointer;
    }

    .createGroup {
        font-weight: bold;
        cursor: pointer;
        height: 60px;
        line-height: 60px;
        padding-left: 20px;
        margin-bottom: 20px;
    }

    .red {
        color: red;
    }
</style>
