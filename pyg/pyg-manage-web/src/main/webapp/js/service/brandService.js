//服务抽取
app.service("brandServcie",function ($http) {
    this.findPage=function (page,rows) {
        return $http.get("../brand/findPage.do?page="+page+"&rows="+rows);
    }
    this.search=function (page,rows,searchEntity) {
        return $http.post("../brand/search.do?page="+page+"&rows="+rows,searchEntity);
    }
    this.save=function (entity) {
        return $http.post("../brand/add.do",entity);
    }
    this.update=function (entity) {
        return $http.post("../brand/update.do",entity);
    }
    this.saveOrUpdate=function (entity,url) {
        return $http.post("../brand/"+url+".do",entity);
    }
    this.findOne=function (id) {
        return $http.get("../brand/findOne.do?id="+id);
    }
    this.delete=function (ids) {
        return $http.get("../brand/delete.do?ids="+ids);
    }
});