<template>
    <div class="content">
        <div class="header">
            <div class="backButton" @click="$router.push('/main')">< Back</div>
            <div class="title">
                <span @click="$router.push('/main/friendInfo')">{{$store.state.queryResult.name}}</span>
            </div>
        </div>
        <div class="msg">
            <div class="msgBox" id="scroll">
                <div class="msgRow" v-for="msg in $store.state.recMsg"
                     v-if="msg.senderId===$store.state.queryResult.id || (msg.senderId === $store.state.currentUser.id && msg.receiverId === $store.state.queryResult.id)">
                    <span class="senderName">{{msg.name}}</span>
                    <span class="receiveTime">{{msg.time}}</span>
                    <br>
                    <span class="msgContent">{{msg.msg}}</span>
                </div>
            </div>
            <div class="inputBox">
                <div class="toolBar">
                    <div class="tool" @click="$router.push('/main/friendmsglog')">聊天记录</div>
                </div>
                <textarea class="textarea" v-model="chatMsg" @keyup.enter="sendMsg(chatMsg)">
            </textarea>
                <div class="bottomBar">
                    <div class="sendButton" @click="sendMsg(chatMsg)">发送</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import router from "../router";

    export default {
        name: "FriendChat",
        data() {
            return {
                msgUserId: null,
                chatMsg: null,
            }
        },
        mounted() {
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
        methods: {
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
                    this.msgUserId = data.chatMsg.senderId;
                    this.scroll();
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
            // 发送消息
            sendMsg(msg) {
                msg = msg.replace('\n', '');
                if (!msg) {
                    this.chatMsg = null;
                    alert("发送内容不能为空，请重新输入")
                } else {
                    let chatReq = {
                        action: 2,
                        chatMsg: {
                            senderId: null,
                            receiverId: null,
                            msg: null
                        }
                    };
                    chatReq.chatMsg.senderId = this.$store.state.currentUser.id;
                    chatReq.chatMsg.name = this.$store.state.currentUser.name;
                    chatReq.chatMsg.receiverId = this.$store.state.queryResult.id;
                    chatReq.chatMsg.msg = msg.replace('\n', '');
                    this.send(chatReq);
                    // 把发送的消息写入对话框
                    let date = new Date();
                    chatReq.chatMsg.time = date.toLocaleString('chinese', {hour12: false});
                    chatReq.chatMsg.senderName = this.$store.state.currentUser.name;
                    this.$store.commit("pushRecMsg", chatReq.chatMsg);
                    this.chatMsg = null;
                    this.scroll();
                }
            },
            scroll() {
                this.$nextTick(() => {
                    let msg = document.getElementById('scroll');
                    msg.scrollTop = msg.scrollHeight
                })
            },
            // fileUpload(e) {
            //     let file = e.target.files[0];
            //     let param = new FormData();
            //     param.append('file', file);
            //     console.log(param.get('file'));
            //     let config = {
            //         headers: {'Content-Type': 'multipart/form-data'}
            //     }; //添加请求头
            //     this.axios.post('/file/up', param, config)
            //         .then(response => {
            //             console.log(response.data);
            //         })
            // }
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

    .title > span {
        cursor: pointer;
    }

    .backButton {
        position: absolute;
        cursor: pointer;
        font-size: 20px;
        color: #888888;
    }

    .toolBar {
        width: 100%;
        height: 30px;
    }

    .textarea {
        width: 644px;
        height: 80%;
        padding: 5px;
        outline: none;
        resize: none;
        border: none;
        background: #EEEEEE;
        font-size: 20px;
        font-family: Arial, serif;
    }

    .bottomBar {
        text-align: right;
    }

    .bottomBar > span {
        color: #888888;
        font-size: 10px;
        margin-right: 20px;

    }

    .sendButton {
        display: inline-block;
        border: 1px solid #c1c1c1;
        text-decoration: none;
        background-color: #fff;
        color: #222;
        border-radius: 4px;
        padding: 3px 30px;
        font-size: 14px;
        cursor: pointer;
        user-select: none;
    }

    .sendButton:hover {
        background-color: #d8d8d8;
    }

    .senderName {
        margin-right: 20px;
        font-weight: bold;
    }

    .msgContent {
        word-wrap: normal;
    }

    .msgRow {
        padding: 5px;
        border-bottom: 1px solid #c1c1c1;
        line-height: 25px;
    }

    .msgRow:last-child {
        border: none;
    }

    .receiveTime {
        float: right;
    }

    .msg {
        height: 100%;
        box-sizing: border-box;
    }

    .msgBox {
        height: 70%;
        overflow-y: scroll;
        margin-top: 60px;
    }

    .inputBox {
        width: 650px;
        height: 25%;
        border-top: 1px solid #CCCCCC;
    }

    .tool {
        width: 80px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        cursor: pointer;
        border-right: 1px solid #CCCCCC;
    }

    .toolBar {
        border-bottom: 1px solid #CCCCCC;
        height: 30px;
    }
</style>
