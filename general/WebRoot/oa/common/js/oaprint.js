$(function () {



});

var showComment = function () {
    var href = window.location.href;

    var processidRegex = /processid=\d+/g;
    var pricessidArr = href.match(processidRegex);
    var processid = pricessidArr[0].match(/\d+/)[0];

    $.ajax({
        type: 'get',
        url: '../act/getCommentImgInfo.do?processid=' + processid,
        dataType: 'json',
        success: function (commentArr) {

            var html = new Array();
            var taskcount = 0;
            $('.loading').html('');

            commentArr.sort(function (o1, o2) {

                if((o1.task && o1.task[0] && o1.task[0].info5)
                    && (o2.task && o2.task[0] && o2.task[0].info5)) {
                    return o1.task[0].info5.localeCompare(o2.task[0].info5);
                } else if((o1.task && o1.task[0] && o1.task[0].info5)) {
                    return -1;
                } else if((o2.task && o2.task[0] && o2.task[0].info5)) {
                    return 1;
                } else {
                    return 0;
                }

            });

            for(var i in commentArr) {

                var comment = commentArr[i];
                if(comment.task && comment.task[0] && comment.task[0].endtime) {

                    var taskname = comment.taskName;
                    var assignee = comment.task[0].handlename;
                    var time = comment.task[0].endtime.substring(0, 10);
                    if(taskcount > 0) {
                        html.push('<div style="width: 20px; float: left; font-size: 13px;"><br/>-></div>');
                    }
                    html.push('<div style="width: 80px; font-size: 13px; float: left;">');
                    html.push('<div style="width: 80px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">' + taskname + '</div>');
                    html.push('<div style="width: 80px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">' + assignee + '</div>');
                    html.push('<div style="width: 80px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; height: 30px;">' + time + '</div>');
                    html.push('</div>');

                    taskcount ++ ;
                }
            }

            if(taskcount > 0) {
                $('.loading').html(html.join(''));
            }
        }
    })
};