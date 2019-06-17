<%@page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="common/head.jsp"%>
</head>
<body>
   <div class="container">
       <div  class="panel panel-default text-center">
           <div class="panel-heading">${seckill.name}
           </div>
           <div class="panel-body">
               <h2 class="text-danger">
                   <!-- 显示time图标-->
                   <span class="glyphicon glyicon-time"></span>
                   <!-- 显示倒计时-->
                   <span class="glyicon" id="seckill-box"></span>
               </h2>
           </div>
       </div>
   </div>
    <!-- 登录弹出层  输入电话   model默认是隐藏的-->
    <div id="killPhoneModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <h3 class="modal-title text-center">
                        <span class="glyicon glyicon-phone"></span>秒杀电话：
                    </h3>
                </div>

                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-8 col-offset-2">
                            <input type="text" name="killPhone" id="killPhoneKey"
                            placeholder="填写手机号" class="form-control"/>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <!-- 验证信息-->
                    <span id="killPhoneMessage" class="glyicon"></span>
                    <button type="button" id="killPhoneBth" class="btn btn-success">
                        <span class="glyphicon glyphicon-phone"></span>
                        提交
                    </button>
                </div>
            </div>

        </div>

    </div>
</body>

<%--<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->--%>
<%--<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>--%>
<%--<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->--%>
<%--<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>

<script src="${pageContext.request.contextPath}/resources/plugins/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap-3.3.0/js/bootstrap.min.js"></script>
<!-- 引入cdn服务-->
<script src="${pageContext.request.contextPath}/resources/plugins/jquery.cookie.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery.countdown.min.js"></script>
<!-- 交互页面-->
<script src="${pageContext.request.contextPath}/resources/script/seckill.js"></script>
<script type="text/javascript">
    $(function () {
        //使用EL表达式参入参数
        seckill.detail.init({
            seckillId:${seckill.seckillId},
            startTime:${seckill.startTime.time}, //毫秒时间
            endTime:${seckill.endTime.time}
        })
    })
</script>
</html>
