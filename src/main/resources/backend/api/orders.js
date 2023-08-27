// 查询列表页接口
const getOrderDetailPage = (params) => {
    return $axios({
        url: '/ordersDetail/page',
        method: 'get',
        params
    })
}

// 查看接口
const queryOrderDetailById = (id) => {
    return $axios({
        url: `/ordersDetail/${id}`,
        method: 'get'
    })
}

// 取消，派送，完成接口
const editOrderDetail = (params) => {
    return $axios({
        url: '/ordersDetail',
        method: 'put',
        data: {...params}
    })
}
