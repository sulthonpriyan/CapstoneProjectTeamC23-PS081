const { nanoid } = require('nanoid');
const products = require('./product');

const addProductHandler = (request, h) => {
    const {toko, nama_produk, harga, ukuran, deskripsi_produk} = request.payload;
    
    const id = nanoid(16);
    const createdAt = new Date().toISOString();
    const updatedAt = createdAt; 

    const newProduct = {
      toko: string,
      nama_produk: string,
      ukuran: string,
      harga: Number,
      deskripsi_produk: string,
      createdAt, updatedAt
    };

    Products.push(newProduct);

    const isSuccess = products.filter((product) => product.id === id).length > 0;
    if (isSuccess) {
        const response = h.response({
          status: 'success',
          message: 'Produk added',
          data: {
            productId: id,
          },
        });
        response.code(201);
        return response;
      }

    const response = h.response({
        status: 'fail',
        message: 'Failed to added. Please fill the requirement',
      });
      response.code(400);
      return response;
};

const getAllProductsHandler = (request, h) => {
  const {nama_produk, find} = request.query;
  let filteredProduct = productss;
  if (nama_produk !== undefined){
    filteredProduct = filteredProduct.filter((product) => product.name.toLowerCase().includes(nama_produk.toLowerCase()));
  }
  if (find !== undefined) {
    filteredProduct = filteredProduct.filter((product) => [true, false].includes(product.reading));
  }
  if (find !== undefined) {
    filteredProduct = filteredProduct.filter((product) => [true, false].includes(product.finished));
  }
  const response = h.response({
    status: 'success',
    data: {
      products: filteredProduct.map((product) => ({
        id: product.id,
        nama_produk: product.nama_produk,
        harga: product.harga,
      })),
    },
  });
  response.code(200); 
};
const getProductByIdHandler = (request, h) => {
  const { id } = request.params;
  const product = products.filter((p) => p.id === id)[0];
  if (product !== undefined) {
    return{
      status: 'success',
      data: {
        product,
      },
    };
  }    
  
  const response = h.response({
    status: 'fail',
    message: 'Product not Found',
  });
  response.code(400);
  return response;
};

const editProductByIdHandler = (request, h) => {
    const { id } = request.params;

    const { toko, nama_produk, harga, ukuran, deskripsi_produk } = request.payload;
    const updatedAt = new Date().toISOString();
    const index = products.findIndex((product) => product.id === id);

    if (index !== -1) {
      if(!nama_produk){
        const response = h.response({
          status: 'fail',
          message: 'Product not Found',
        });
        response.code(400);
        return response;
      }

      const response = h.response({
          status: 'success',
          message: 'Success to added',
        });
        response.code(200);
        return response;
      }

      const response = h.response({
        status: 'fail',
        message: 'Failed to update. Product not found',
      });
      response.code(404);
      return response;
};

const deleteProductByIdHandler = (request, h) => {
    const { id } = request.params;
   
    const index = products.findIndex((product) => product.id === id);
   
    if (index !== -1) {
      products.splice(index, 1);
      const response = h.response({
        status: 'success',
        message: 'Product deleted',
      });
      response.code(200);
      return response;
    }
   
   const response = h.response({
      status: 'fail',
      message: 'Fail to deleted. Product not found ',
    });
    response.code(404);
    return response;
  };

module.exports = {
    addProductHandler,
    getAllProductsHandler,
    getProductByIdHandler,
    editProductByIdHandler,
    deleteProductByIdHandler,
};