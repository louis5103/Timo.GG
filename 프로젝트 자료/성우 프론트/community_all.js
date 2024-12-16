function goLogin() {
  window.location.href = "./login.html";
}

window.onload = function () {
  let getJsonData = function () {
    const xhttp = new XMLHttpRequest();
    const output = document.querySelector("[ref=output]");
    xhttp.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
        const resObj = JSON.parse(this.responseText);
        const list = resObj.data.list;
        list.forEach(function (item) {
          const row = document.createElement("tr");

          const likeCell = document.createElement("td");
          likeCell.textContent = item.like;
          row.appendChild(likeCell);

          const titleCell = document.createElement("td");
          const titleLink = document.createElement("a");
          titleLink.href = `community_writed.html?id=${item.id}`;
          titleLink.textContent = item.title;
          titleCell.appendChild(titleLink);
          row.appendChild(titleCell);

          output.appendChild(row);
        });
      }
    };
    xhttp.open("GET", "./write.json", true); // 여기에 데이터 받을 주소
    xhttp.setRequestHeader(
      "Content-type",
      "application/x-www-form-urlencoded; charset=utf-8"
    );
    xhttp.send();
  };

  getJsonData();
};
