window.$ = window.jQuery = require('jquery')
import '@fortawesome/fontawesome-free/js/all';
import 'animate.css'
import '../scss/styles.scss'
import '@fengyuanchen/validator';
import CriarLista from './pages/criaLista'
import EditarLista from './pages/editaLista'
import MinhasListas from './pages/minhaLista'
import RealizarPedido from './pages/realizaPedido'
import Pedidos from './pages/pedidos'
import Pedido from './pages/pedido'
import Modal from './components/modal'
import Burger from './components/menu-burger'
import EditarPedido from './pages/editaPedido'
import Produtos from './pages/produtos'
import CadastraProduto from './pages/cadastraProduto'
import validaCadastraProduto from './pages/validaCadastraProduto'



$(function () {
    CriarLista.validaFormulario();   
    EditarLista.validaFormulario();
    CadastraProduto.atualizaUnidadeMedida();
    validaCadastraProduto.validaFormulario();


    window.MinhasListas = MinhasListas;
    window.EditarLista = EditarLista;
    window.RealizarPedido = RealizarPedido;
    window.Pedidos = Pedidos;
    window.Pedido = Pedido;
    window.CriarLista = CriarLista;
    window.Modal = new Modal();
    window.EditaPedido = EditarPedido;
    window.Burger = Burger;
    window.Produtos = Produtos;
    window.CadastraProduto = CadastraProduto;

});
