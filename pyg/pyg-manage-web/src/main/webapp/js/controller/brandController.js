app.controller("brandController", function ($scope,$controller,brandServcie) {
    //继承baseController(伪继承),可以使用baseController中定义的方法和变量
    $controller("baseController",{$scope:$scope});
    $scope.searchEntity={};
    //加载以及查询都是这个方法
    $scope.search = function (page,rows) {
        brandServcie.search(page,rows,$scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems=response.total;
        });
    }
    /*真正请求数据的方法*/
    $scope.findPage = function (page,rows) {
        brandServcie.findPage(page,rows).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems=response.total;
        });
    };
    //保存或者更新新的品牌
    $scope.save=function () {
        var obj;
        if($scope.entity.id){
            obj=brandServcie.saveOrUpdate($scope.entity,"update");
        }else{
            obj=brandServcie.saveOrUpdate($scope.entity,"add");
        }
        obj.success(function (response) {
            if (response.success){
                $scope.reloadList();
            }else{
                alert(response.message);
            }
        });
    }
    //根据id查询
    $scope.findOne=function (id) {
        brandServcie.findOne(id).success(function (response) {
            $scope.entity=response;
        });
    }
    //根据id批量删除
    $scope.delete=function () {
        if($scope.selectedIds.length<1){
            alert("请先选择要删除的记录");
            return;
        }
        if(confirm("确定删除吗?")){
            brandServcie.delete($scope.selectedIds).success(function (response) {
                if(response.success){
                    $scope.reloadList();
                    $scope.selectedIds=[];
                }else{
                    alert(response.message);
                }
            });
        }
    }
});