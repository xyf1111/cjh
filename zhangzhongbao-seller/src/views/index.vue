<template>
    <div class="indexDiv" :style="indexDiv"></div>
</template>

<script>
export default {
  name: 'index',
  data () {
    return {
      indexDiv: {
        fontSize: '50px',
        width: '100%',
        height: '100%',
        backgroundImage: 'url(' + require('@/assets/zzb.jpg') + ')',
        backgroundSize: '100% 100%',
        backgroundRepeat: ' no-repeat'
      }
    }
  }
}
</script>

<style scoped>
  .index-div{
    font-size: 50px;
    width: 100%;
    height: 100%;
    background-image: url('../assets/zzb.jpg');
    background-size: 100% 100%;
    background-repeat: no-repeat
  }
</style>
