$(document).ready(function() {
    "use strict";
    document.title = "Manage RSS Feeds";

    $(".breadcrumb").nfBreadcrumbs({
        crumbs : [
            { text : "Feeds" }
        ]
    });

    var feedsData = [];

    function loadFeeds() {
        $.get('/feeds', function(data) {
            feedsData = [];
            var feedIndex, feedInfo;
            if (data.subscriptions instanceof Array) {
                for (feedIndex in data.subscriptions) {
                    feedInfo = data.subscriptions[feedIndex];
                    feedsData.push({title : feedInfo.title, url: feedInfo.url});
                }
            } else {
                feedsData.push({title : data.subscriptions.title, url: data.subscriptions.url});
            }
            populateFeedsTable();
        });

    }

    function populateFeedsTable() {
        var oTable = $('#feeds-table').dataTable( {
            "aoColumns": [
                { "sTitle": "Title", mDataProp: 'title', "sWidth" : "50%", "sDefaultContent": "-"},
                { "sTitle": "Link", mDataProp : "url", "sWidth" : "40%", "sDefaultContent": "-" },
                {"sTitle": "", "sWidth": "10", sDefaultContent : '-',
                    "fnRender": function (oObj) {
                        return '<a href="' + oObj.aData.url + '" title=\"Delete feed\" class="feed-delete"><i class="icon-remove"></i></a>' +
                        '<a href="#" title=\"Edit Feed\" class="feed-edit"><i class="icon-edit"></i></a>';
                    }
                }
            ],
            "aaData"         : feedsData,
            "bDestroy"       : true,
            "bAutoWidth"     : false,
            "bStateSave"     : true,
            "bPaginate"      : false,
            "bLengthChange"  : false
        });
    }

    $('.feed-delete').live('click', function(evt){
        evt.preventDefault();
        var linkElm = $(evt.target).parent()[0];
        console.log(linkElm);
        var feedURL = $(linkElm).attr('href');


        $.nfConfirm({
            title       : "Delete feed?",
            text        : "Do you really want to delete feed <strong>'{0}'</strong>?\n".format(feedURL),
            proceed     : function() {
                $.ajax({
                    type     : "DELETE",
                    url      : '/feeds',
                    data     : { feedURL: feedURL},
                    dataType : "json",
                    success  : function(result) {
                        loadFeeds();
                    },
                    error    : function(jqXHR, textStatus, errorThrown) {
                        $.nfAlert({title: "Error", text : "Error deleting feed. " + jqXHR.responseText});
                    }
                });
            },
            fail      : function(err) {
                $.nfAlert({title: "Error" , text : "Error deleting feed. " + err});
            }
        });
    });


    $('.feed-new').live('click', function(evt) {
        $("#feed-add-template").nfTemplateDialog({
            payloadType : "object",
            sSuccess    : 'New feed created',
            sSuccessDelay : 300,
            oRules : {
                "dsl" : {required: true}
            },
            fnSuccess : function() {
                loadFeeds();
            }
        });
    });


    loadFeeds();
});