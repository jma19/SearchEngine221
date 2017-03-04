define([
    'backbone',
    'handlebars',
    '../template/result-table.hbs'
], function (Backbone, Handlebars, ResultTableTpl) {

    return Backbone.View.extend({
        template: Handlebars.compile(ResultTableTpl),
        render: function (records) {
            this.$el.html(this.template({ records: records }));
        }
    });
});