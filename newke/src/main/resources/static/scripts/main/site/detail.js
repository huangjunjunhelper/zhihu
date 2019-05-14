(function (window, undefined) {
    var Action = Base.createClass('main.util.Action');

    Base.ready({
        initialize: fInitialize,
        events: {
            'click .js-like': fVote,
            'click .js-unlike': fVote
        }
    });

    function fInitialize() {
        var that = this;
    }

    function fVote(oEvent) {
        var that = this;
        var oEl = $(oEvent.currentTarget);
        var oDv = oEl.closest('div.js-vote');
        var sId = $.trim(oDv.attr('data-id'));
        var bLike = oEl.hasClass('js-like');
        if (!sId) {
            return;
        }
        if (that.isVote) {
            return;
        }
        that.isVote = true;
        Action[bLike ? 'like' : 'dislike']({
            commentId: sId,
            call: function (oResult) {
                if (oResult.code === 999) {
                    window.location.href = '/login';
                    return;
                }
                // 调整样式
                oDv.find('.pressed').removeClass('pressed');
                oDv.find(bLike ? '.js-like' : '.js-unlike').addClass('pressed');
                // 更新数量
                oDv.find('span.js-count').html(oResult.count);
            },
            error: function (oResult) {
                alert('出现错误，请重试');
            },
            always: function () {
                that.isVote = false;
            }
        });
    }

    function fUnlike(oEvent) {
        var that = this;
        var oEl = $(oEvent.currentTarget);

    }

})(window);