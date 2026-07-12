
const menuBtn = document.getElementById("menuBtn");
const sideMenu = document.getElementById("sideMenu");

const close = document.getElementById("close");


const userBtn = document.getElementById("iconButton2");
const userMenu = document.getElementById("userSideMenu");

const userClose = document.getElementById("userClose");

  


menuBtn.onclick = () => {
  
    const left = window.getComputedStyle(sideMenu).left;

    if (left === "-250px") {
        const rightt = window.getComputedStyle(userMenu).right;

        if (rightt === "0px") {
            userMenu.style.right = "-250px";  
        } 
        sideMenu.style.left = "0px";  
    } 
};


close.onclick = () => {
 
    const leftt = window.getComputedStyle(sideMenu).left;

    if (leftt === "0px") {
        sideMenu.style.left = "-250px";  
    } 
};





document.addEventListener("click", (e) => {
    const left = window.getComputedStyle(sideMenu).left;
    const right = window.getComputedStyle(userMenu).right;

    if (
        left === "0px" &&
        !sideMenu.contains(e.target) &&
        !menuBtn.contains(e.target) &&
        !userMenu.contains(e.target) &&
        !userBtn.contains(e.target)  // FIXED
    ) {
        sideMenu.style.left = "-250px";
    }

     if (
        right === "0px" &&
        !sideMenu.contains(e.target) &&
        !menuBtn.contains(e.target) &&
        !userMenu.contains(e.target) &&
        !userBtn.contains(e.target)  // FIXED
    ) {
        userMenu.style.right = "-250px";
    }
});


userBtn.onclick = () => {
  
    const right = window.getComputedStyle(userMenu).right;

    if (right === "-250px") {
        const leftt = window.getComputedStyle(sideMenu).left;

        if (leftt === "0px") {
            sideMenu.style.left = "-250px";  
        } 
        userMenu.style.right = "0px";  
    } 
};









userClose.onclick = () => {
 
    const rightt = window.getComputedStyle(userMenu).right;

    if (rightt === "0px") {
        userMenu.style.right = "-250px";  
    } 
};