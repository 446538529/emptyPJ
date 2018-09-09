app.controller("baseController",function ($scope) {
    /*分页配置对象*/
    $scope.paginationConf={
        currentPage:1,
        totalItems:10,
        itemsPerPage:10,
        perPageOptions:[10,20,30,40,50],
        onChange:function () {
            $scope.readList();
        }
    };
    /*改变任何参数都重新加载*/
    $scope.readList=function () {
        //$scope.findPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
        $scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
    }
    //更新选中的记录的id数组
    $scope.selectedIds=[];
    $scope.updateSelections=function ($event,id) {
        if($event.target.checked){
            $scope.selectedIds.push(id);
        }else{
            var index=$scope.selectedIds.indexOf(id);
            $scope.selectedIds.splice(index,1);
        }
    }
});