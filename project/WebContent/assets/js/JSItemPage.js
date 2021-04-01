(
    ()=>{
      const imgHovers=document.querySelectorAll('.imgHover img');
      const showImgActive=document.querySelector('.showImg-active');
      const active=document.querySelector('.slider_1');
      //console.log(active);
      active.classList.add('listImg--active');
      //console.log('showImgActive:',showImgActive.src);
      //mouseover vào phần không phải la img thi event trong addEventListener sẽ khong tim thay duong dan đênt ảnh
      imgHovers.forEach((img)=>{
        img.addEventListener('mouseover',e=>{
          //console.log('event:',e.target.src)
          let nameImg=e.target.src;
          showImgActive.src=nameImg;
          imgHovers.forEach(element=>element.parentElement.classList.remove('listImg--active'));
          e.target.parentElement.classList.add('listImg--active');
        })
      })
    },
    ()=>{
      const showNum=document.querySelector('.prodct__amount--current');
      const operator=document.querySelectorAll('.prodct__amount--operator');
      const available=parseInt(document.querySelector('#product__available').textContent);
      let num=parseInt(showNum.textContent);
      operator.forEach((element)=>{
        element.addEventListener('click',()=>{
          if(num>0 && num<=available){
            num=element.getAttribute('id')==='plus'?(num===available?num:++num):(num===1?num:--num);
            showNum.textContent=num;
          }
        })
      })
    }
  )()