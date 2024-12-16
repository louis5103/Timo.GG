function goLogin() {
  window.location.href = "./login.html";
}

window.onload = function () {
  // URL 파라미터에서 ID 추출
  const urlParams = new URLSearchParams(window.location.search);
  const postId = urlParams.get("id");

  if (postId) {
    // 게시글 내용을 서버에서 받아오기
    const xhttp = new XMLHttpRequest();
    const postContent = document.getElementById("post-content");
    xhttp.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
        const resObj = JSON.parse(this.responseText);
        const post = resObj.data.list.find((item) => item.id === postId);
        if (post) {
          postContent.value = `${post.content}`;
        } else {
          postContent.value = "게시글을 찾을 수 없습니다.";
        }
      }
    };
    xhttp.open("GET", "./write.json", true); // 게시글 내용을 받을 주소
    xhttp.setRequestHeader(
      "Content-type",
      "application/x-www-form-urlencoded; charset=utf-8"
    );
    xhttp.send();
  } else {
    postContent.value = "게시글을 찾을 수 없습니다.";
  }
};
