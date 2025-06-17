import{s as n}from"./B-kZlFfU.js";import{v as i,B as p,c as s,o,P as e,a}from"./DWUyzV_3.js";var l=i`
    .p-progressspinner {
        position: relative;
        margin: 0 auto;
        width: 100px;
        height: 100px;
        display: inline-block;
    }

    .p-progressspinner::before {
        content: '';
        display: block;
        padding-top: 100%;
    }

    .p-progressspinner-spin {
        height: 100%;
        transform-origin: center center;
        width: 100%;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        margin: auto;
        animation: p-progressspinner-rotate 2s linear infinite;
    }

    .p-progressspinner-circle {
        stroke-dasharray: 89, 200;
        stroke-dashoffset: 0;
        stroke: dt('progressspinner.colorOne');
        animation:
            p-progressspinner-dash 1.5s ease-in-out infinite,
            p-progressspinner-color 6s ease-in-out infinite;
        stroke-linecap: round;
    }

    @keyframes p-progressspinner-rotate {
        100% {
            transform: rotate(360deg);
        }
    }
    @keyframes p-progressspinner-dash {
        0% {
            stroke-dasharray: 1, 200;
            stroke-dashoffset: 0;
        }
        50% {
            stroke-dasharray: 89, 200;
            stroke-dashoffset: -35px;
        }
        100% {
            stroke-dasharray: 89, 200;
            stroke-dashoffset: -124px;
        }
    }
    @keyframes p-progressspinner-color {
        100%,
        0% {
            stroke: dt('progressspinner.color.one');
        }
        40% {
            stroke: dt('progressspinner.color.two');
        }
        66% {
            stroke: dt('progressspinner.color.three');
        }
        80%,
        90% {
            stroke: dt('progressspinner.color.four');
        }
    }
`,c={root:"p-progressspinner",spin:"p-progressspinner-spin",circle:"p-progressspinner-circle"},d=p.extend({name:"progressspinner",style:l,classes:c}),g={name:"BaseProgressSpinner",extends:n,props:{strokeWidth:{type:String,default:"2"},fill:{type:String,default:"none"},animationDuration:{type:String,default:"2s"}},style:d,provide:function(){return{$pcProgressSpinner:this,$parentInstance:this}}},f={name:"ProgressSpinner",extends:g,inheritAttrs:!1,computed:{svgStyle:function(){return{"animation-duration":this.animationDuration}}}},h=["fill","stroke-width"];function m(r,k,u,y,v,t){return o(),s("div",e({class:r.cx("root"),role:"progressbar"},r.ptmi("root")),[(o(),s("svg",e({class:r.cx("spin"),viewBox:"25 25 50 50",style:t.svgStyle},r.ptm("spin")),[a("circle",e({class:r.cx("circle"),cx:"50",cy:"50",r:"20",fill:r.fill,"stroke-width":r.strokeWidth,strokeMiterlimit:"10"},r.ptm("circle")),null,16,h)],16))],16)}f.render=m;export{f as default};
