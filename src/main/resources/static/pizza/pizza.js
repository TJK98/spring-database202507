const URL = '/api/v1/pizza-menus';

const $form = document.getElementById('pizza-form');
const $tbody = document.getElementById('pizza-tbody');

// 등록 요청
$form.onsubmit = async (e) => {
    e.preventDefault();

    const name = document.getElementById('name').value.trim();
    const description = document.getElementById('description').value.trim();
    const price = parseFloat(document.getElementById('price').value);

    if (!name || isNaN(price)) {
        alert('이름과 가격은 필수입니다.');
        return;
    }

    const pizza = { name, description, price, available: true };

    const res = await fetch(URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(pizza)
    });

    const msg = await res.text();
    alert(msg);
    fetchPizzas();
    $form.reset();
};

// 전체 조회
async function fetchPizzas() {
    const res = await fetch(URL);
    const pizzas = await res.json();

    $tbody.innerHTML = '';

    pizzas.forEach(pizza => {
        const $tr = document.createElement('tr');
        $tr.innerHTML = `
      <td>${pizza.id}</td>
      <td>${pizza.name}</td>
      <td>${pizza.description ?? '-'}</td>
      <td>${pizza.price}</td>
      <td>${pizza.available ? 'O' : 'X'}</td>
      <td>${pizza.createdAt.replace('T', ' ')}</td>
      <td>${pizza.updatedAt.replace('T', ' ')}</td>
      <td>
        <button onclick="deletePizza(${pizza.id})">삭제</button>
      </td>
    `;
        $tbody.appendChild($tr);
    });
}

// 삭제 요청
async function deletePizza(id) {
    if (!confirm(`${id}번 메뉴를 삭제하시겠습니까?`)) return;

    const res = await fetch(`${URL}/${id}`, { method: 'DELETE' });
    const msg = await res.text();
    alert(msg);
    fetchPizzas();
}

// 최초 실행
fetchPizzas();