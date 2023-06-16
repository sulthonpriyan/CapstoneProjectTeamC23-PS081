const {
    addProductHandler,
    getAllProductsHandler,
    getProductByIdHandler,
    editProductByIdHandler,
    deleteProductByIdHandler,
  } = require('./handler');
const routes = [
    {
      method: 'POST',
        path: '/produk',
        handler: addProductHandler,
    },
    {
        method: 'GET',
        path: '/produk',
        handler: getAllProductsHandler,
    },
    {
      method: 'GET',
      path: '/produk/{id}',
      handler: getProductByIdHandler,
  },
    {
        method: 'PUT',
        path: '/produk/{id}',
        handler: editProductByIdHandler,
    },
    {
        method: 'DELETE',
        path: '/produk/{id}',
        handler: deleteProductByIdHandler,
    },
  ];
   
  module.exports = routes;
