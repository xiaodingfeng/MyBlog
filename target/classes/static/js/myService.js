layui.use(['form','jquery','layer'], function() {
    var form = layui.form
        ,layer = layui.layer;
    var $ = layui.jquery;


    var E = window.wangEditor
    var editor = new E('#editor')
    //开启debug模式
    editor.customConfig.debug = true;
    // 自定义字体
    editor.customConfig.fontNames = [
        '宋体',
        '微软雅黑',
        'Arial',
        'Tahoma',
        'Verdana'
    ]
    var $contentEd = $('#contentEd')
    editor.customConfig.onchange = function () {
        // 监控变化，同步更新到 textarea
        $contentEd.val(editor.txt.html())
    }
    // 关闭粘贴内容中的样式
    editor.customConfig.pasteFilterStyle = false
    editor.customConfig.zIndex = 100
    // 忽略粘贴内容中的图片
    editor.customConfig.pasteIgnoreImg = true
    // 使用 base64 保存图片
    editor.customConfig.uploadImgShowBase64 = true
    // 上传图片到服务器
    editor.customConfig.uploadFileName = 'file'; //设置文件上传的参数名称
    editor.customConfig.uploadImgServer = '/uploadImage'; //设置上传文件的服务器路径
    editor.customConfig.uploadImgMaxSize = 10 * 1024 * 1024; // 将图片大小限制为 10M
    editor.customConfig.uploadImgMaxLength = 5;
    editor.customConfig.uploadImgHooks = {

        success: function (xhr, editor, result) {

        },
        fail: function (xhr, editor, result) {
            layer.msg(result.msg,{icon: 5},{shift: 6});
        },
        error: function (xhr, editor) {
            layer.msg("上传出错了哦~具体原因我也不知道",{icon: 5},{shift: 6});
        }

    }
    editor.create()


    //监听提交
    form.on('submit(*)', function(data){
        var url = $(data.form).attr('action'),type=$(data.form).attr('method'), button = $(data.elem);
        try {
            var index = layer.load();
            $.ajax({
                async: false,
                type: type,
                dataType: 'json',
                data: data.field,
                url: url,
                success: function(res){
                    layer.close(index);
                    if(res.status == 0) {
                        layer.msg(res.msg,{icon: 1},function(){
                                location.href="/";
                        });
                    } else {
                        layer.msg(res.msg,{icon: 5},{shift: 6});
                    }
                }, error: function(e){
                    layer.close(index);
                    layer.msg('请求异常，请重试', {shift: 6},{icon: 5});
                }
            });
        }catch (e) {
            layer.msg(e, {shift: 6},{icon: 5});
        }
        return false;
    });
    form.on('select(category)', function (data) {
        $("#categoryDelete").val(data.value)
    });
    $("#removeCate").on('click', function() {
        var id=$("#categoryDelete").val();
        layer.confirm('想清楚了哦，该类别下所有已上传文章图片也会删除，确定删除？', {
            btn: ['十分确定', '我再想想']
        }, function(index, layero){
            $.ajax({
                url:"/deleteCategory",
                data:{'id':id},
                type:"Post",
                dataType:"json",
                success:function(data){
                    if(data.status==0){
                        layer.msg(data.msg);
                        location.reload();
                    }
                    else{
                        layer.msg(data.msg);
                    }
                },
                error:function(data){
                    layer.msg(data.msg);
                }
            });
        }, function(index){
        });

    });


})