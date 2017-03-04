define([
    'backbone',
    'handlebars',
    'resultTableView',
    '../collection/records',
    'text!../template/search-form.hbs'
], function (Backbone, Handlebars, ResultTableView, RecordList, SearchFormTpl) {
    return Backbone.View.extend({
        template: Handlebars.compile(SearchFormTpl),

        events: {
            'click .search-button': 'doSearch',
            'keypress input[name="keyword"]':'doSearch'
        },

        initialize: {
            resultTableView : new ResultTableView,
            recordList: new RecordList
        },
        render: function () {
          this.$el.html(this.template({ keyword: this.options.keyword}));
        },

        doSearch: function (e) {
            if (e.type == 'click' ||e.keyCode == 13) {
                var keyword = this.$('input[name="keyword"]').val();

            this.recordList.url = 'miya/query?query=' + keyword;
                this.recordList.fetch({
                    success: function (collection, resp, options) {
                        this.resultTableView.render({ records: collection});
                    },
                    error: function (collection, resp, options) {
                        this.resultTableView.render({ error: 'Service Error!' });
                    }
                });
            }
        }
    });
})